package com.example.chatbot.data.repository

import com.example.chatbot.ui.home.UiMessage
import com.example.chatbot.ui.home.chatsession.ChatSession

interface ChatHistoryRepository {
    suspend fun createSession(): String

    suspend fun saveMessage(
        sessionId : String,
        message : UiMessage
    )

    suspend fun loadMessage(
        sessionId: String
    ): List<UiMessage>

    suspend fun updateTitle(
        sessionId: String,
        newTitle : String
    )

    suspend fun getSession(): List<ChatSession>

    suspend fun deleteSession(sessionId : String)
}