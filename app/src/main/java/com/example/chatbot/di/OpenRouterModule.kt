package com.example.chatbot.di

import com.example.chatbot.data.remote.openrouter.provideOpenRouterApi
import com.example.chatbot.data.repository.ChatRepository
import org.koin.dsl.module

val OpenRouterModule = module {
    single { provideOpenRouterApi() }
    single { ChatRepository(get()) }
}