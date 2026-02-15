package com.example.chatbot.data.auth

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImplementation(
    private val firebaseAuth : FirebaseAuth,
    private val firestore: FirebaseFirestore
): AuthRepository {

    private val _isLoggedIn = MutableStateFlow(firebaseAuth.currentUser != null)
    override val isLoggedIn = _isLoggedIn.asStateFlow()

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try{
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Result<Unit> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()

            val uid = result.user?.uid?: throw Exception("User ID not found")

            val profile = UserProfile(
                uid = uid,
                email = email,
                username = username,
            )

            firestore.collection("users")
                .document(uid)
                .set(profile)
                .await()

            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUserProfile(): Result<UserProfile> {
        return try {
            val uid = firebaseAuth.currentUser?.uid?: throw Exception("No logged in user")

            val snapshot = firestore
                .collection("users")
                .document(uid)
                .get()
                .await()

            val profile = snapshot.toObject(UserProfile::class.java)?: throw Exception("Profile not found")

            Result.success(profile)

        } catch (e : Exception){
            Result.failure(e)
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun logout() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
    }

    override fun loginSuccess(){
        _isLoggedIn.value = true
    }

    override suspend fun updateProfile(newName: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser ?: throw Exception("No logged user")


            firestore.collection("users")
                .document(user.uid)
                .update("username", newName)
                .await()

            val profileUpdates = userProfileChangeRequest {
                displayName = newName
            }

            user.updateProfile(profileUpdates).await()

            Result.success(Unit)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}
