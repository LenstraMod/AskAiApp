package com.example.chatbot.data.repository

import com.example.chatbot.data.remote.openrouter.ORMessage
import com.example.chatbot.data.remote.openrouter.ORRequest
import com.example.chatbot.data.remote.openrouter.OpenRouterApi
import com.google.firebase.BuildConfig

class ChatRepository(
    private val api: OpenRouterApi
) {
    suspend fun sendtoAI(text: String): String{

        val request = ORRequest(
            model = "z-ai/glm-4.5-air:free",
            messages = listOf(
                ORMessage("user", text)
            )
        )

        val response = api.chat(
            token = "Bearer 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",
            body = request
        )

        if(response.isSuccessful){
            return response.body()?.choices?.firstOrNull()?.message?.content?: "No response"
        }else{
            throw Exception("HTTP ${response.code()} : ${response.errorBody()?.string()}")
        }
    }
}