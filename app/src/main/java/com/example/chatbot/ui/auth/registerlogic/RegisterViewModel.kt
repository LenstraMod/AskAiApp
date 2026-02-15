package com.example.chatbot.ui.auth.registerlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository : AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(value: String){
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value : String){
        _uiState.update { it.copy(password = value) }
    }

    fun onUsernameChange(value : String){
        _uiState.update { it.copy(username = value) }
    }

    fun register(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errMessage = null) }

            val state = _uiState.value

            val result = repository.register(
                email = state.email,
                password = state.password,
                username = state.username
            )

            result
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                }

                .onFailure { e ->
                    _uiState.update {
                        it.copy(isLoading = false, errMessage = e.message)
                    }
                }
        }
    }

    fun consumeSuccess(){
        _uiState.update { it.copy(isSuccess = false) }
    }

}