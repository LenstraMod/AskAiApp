package com.example.chatbot.data.remote.openrouter

data class ORRequest(
    val model : String,
    val messages : List<ORMessage>
)

data class ORMessage(
    val role: String,
    val content: String,
)

data class ORResponse(
    val choices: List<ORChoice>
)

data class ORChoice(
    val message: ORMessage
)