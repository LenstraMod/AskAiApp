package com.example.chatbot.ui.auth.registerlogic

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",

    val isLoading: Boolean = false,
    val errMessage: String? = null,
    val isSuccess: Boolean = false
)
