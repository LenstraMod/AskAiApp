package com.example.chatbot.ui.home.chatsession

data class ChatSession(
    val id: String,
    var title: String,
    val createdAt : Long = System.currentTimeMillis()
)

data class ChatMessageEntity(
    val id: String = "",
    val text : String = "",
    val isUser: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
