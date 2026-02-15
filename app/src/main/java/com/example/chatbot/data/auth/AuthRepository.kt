package com.example.chatbot.data.auth

import android.net.Uri
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val isLoggedIn: StateFlow<Boolean>
    suspend fun login(
        email : String,
        password : String
    ) : Result<Unit>

    suspend fun register(
        username : String,
        email : String,
        password : String
    ) : Result<Unit>

    suspend fun getCurrentUserProfile(): Result<UserProfile>

    fun isUserLoggedIn(): Boolean

    fun logout()

    fun loginSuccess()

    suspend fun updateProfile(
        newName  : String,
    ) : Result<Unit>

}