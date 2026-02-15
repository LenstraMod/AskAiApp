package com.example.chatbot.ui.profiles

data class ProfileUiState(
    val username: String = "",
    val email:String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
