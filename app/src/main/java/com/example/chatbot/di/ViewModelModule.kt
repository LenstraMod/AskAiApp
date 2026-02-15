package com.example.chatbot.di

import com.example.chatbot.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.chatbot.ui.auth.loginlogic.LoginViewModel
import com.example.chatbot.ui.auth.registerlogic.RegisterViewModel
import com.example.chatbot.ui.home.chatlogic.ChatViewModel
import com.example.chatbot.ui.profiles.ProfileViewModel
import com.example.chatbot.ui.settings.SettingViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(repository = get()) }
    viewModel { RegisterViewModel(repository = get()) }
    viewModel { ProfileViewModel(repository = get()) }
    viewModel { ChatViewModel(get(), get()) }
    viewModel { SettingViewModel(get()) }
    viewModel { MainViewModel(get()) }
}