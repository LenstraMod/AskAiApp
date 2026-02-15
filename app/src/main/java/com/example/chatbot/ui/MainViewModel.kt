package com.example.chatbot.ui

import androidx.lifecycle.ViewModel
import com.example.chatbot.data.auth.AuthRepository

class MainViewModel(
    private val repository : AuthRepository
) : ViewModel() {

    val isLoggedIn = repository.isLoggedIn

    fun logout(){
        repository.logout()
    }

}