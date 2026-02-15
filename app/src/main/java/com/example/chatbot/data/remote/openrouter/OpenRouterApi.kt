package com.example.chatbot.data.remote.openrouter

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterApi {
    @POST("api/v1/chat/completions")
    suspend fun chat(
        @Header("Authorization") token : String,
        @Header("Content-Type") type: String = "application/json",
        @Body body: ORRequest
    ) : Response<ORResponse>
}