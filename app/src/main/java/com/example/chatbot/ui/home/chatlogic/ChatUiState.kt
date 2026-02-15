package com.example.chatbot.ui.home.chatlogic

import com.example.chatbot.ui.home.UiMessage

data class ChatUiState(
    val messages: List<UiMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
