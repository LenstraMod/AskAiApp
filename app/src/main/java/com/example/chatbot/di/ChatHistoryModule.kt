package com.example.chatbot.di

import com.example.chatbot.data.repository.ChatHistoryRepository
import com.example.chatbot.data.repository.FirestoreChatHistoryRepository
import org.koin.dsl.module

val chatHistoryModule = module {
    single<ChatHistoryRepository> {
        FirestoreChatHistoryRepository(get(), get())
    }
}