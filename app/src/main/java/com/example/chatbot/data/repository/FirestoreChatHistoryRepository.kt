package com.example.chatbot.data.repository

import android.util.Log
import com.example.chatbot.ui.home.UiMessage
import com.example.chatbot.ui.home.chatsession.ChatSession
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreChatHistoryRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ChatHistoryRepository {

    private fun uid(): String = auth.currentUser?.uid?: throw Exception("No logged in user")

    private fun sessionRef(sessionId: String) =
        firestore.collection("users")
            .document(uid())
            .collection("chat_sessions")
            .document(sessionId)

    private fun messageRef(sessionId: String) =
        sessionRef(sessionId).collection("messages")


    override suspend fun createSession(): String {
        val ref = firestore.collection("users")
            .document(uid())
            .collection("chat_sessions")
            .document()

        val data = mapOf(
            "id" to ref.id,
            "title" to "New Chat",
            "createdAt" to System.currentTimeMillis()
        )

        ref.set(data).await()

        return ref.id
    }

    override suspend fun saveMessage(sessionId: String, message: UiMessage) {
        val data = mapOf(
            "text" to message.text,
            "isUser" to message.isUser,
            "timestamp" to System.currentTimeMillis()
        )

        messageRef(sessionId).add(data).await()
    }

    override suspend fun loadMessage(sessionId: String): List<UiMessage> {
        val snapshot = messageRef(sessionId)
            .orderBy("timestamp")
            .get()
            .await()

        return snapshot.documents.map { doc->
            UiMessage(
                text = doc.getString("text") ?: "",
                isUser = doc.getBoolean("isUser") ?: false
            )
        }
    }

    override suspend fun updateTitle(sessionId: String, newTitle: String) {
        sessionRef(sessionId)
            .update("title", newTitle)
            .await()
    }

    override suspend fun getSession(): List<ChatSession> {
        val snapshot = firestore
            .collection("users")
            .document(uid())
            .collection("chat_sessions")
            .get()
            .await()

        return snapshot.documents.map { doc->
            ChatSession(
                id = doc.id,
                title = doc.getString("title") ?: "New Chat"
            )
        }
    }

    override suspend fun deleteSession(sessionId: String) {
        val sessionRef = firestore.collection("users")
            .document(uid())
            .collection("chat_sessions")
            .document(sessionId)

        val messages = sessionRef.collection("messages").get().await()

        for(c in messages){
            c.reference.delete().await()
        }

        sessionRef.delete().await()
    }
}