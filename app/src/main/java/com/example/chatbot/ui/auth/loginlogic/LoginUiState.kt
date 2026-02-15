package com.example.chatbot.ui.auth.loginlogic

data class LoginUiState(
    val email : String = "",
    val pass : String = "",

    val isLoading : Boolean = false,
    val errMessage : String? = null,
    val isSuccess : Boolean = false,
)
