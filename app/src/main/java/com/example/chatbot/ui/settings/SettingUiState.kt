package com.example.chatbot.ui.settings

data class SettingUiState(
    val username : String = "",
    val email : String = "",

    val isLoading: Boolean = false,
    val error: String? = null
)
