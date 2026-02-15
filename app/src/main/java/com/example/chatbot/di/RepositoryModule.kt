package com.example.chatbot.di

import com.example.chatbot.data.auth.AuthRepository
import com.example.chatbot.data.auth.AuthRepositoryImplementation
import com.example.chatbot.data.repository.ChatHistoryRepository
import com.example.chatbot.data.repository.ChatRepository
import com.example.chatbot.data.repository.FirestoreChatHistoryRepository
import org.koin.dsl.module

val repositoryModule = module{
    single<AuthRepository> {
        AuthRepositoryImplementation(
            firebaseAuth = get(),
            firestore = get(),
        )
    }

    single<ChatHistoryRepository> {
        FirestoreChatHistoryRepository(get(),get())
    }
}