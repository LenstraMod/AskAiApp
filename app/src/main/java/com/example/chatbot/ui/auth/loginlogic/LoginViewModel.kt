package com.example.chatbot.ui.auth.loginlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository : AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(value: String){
        _uiState.update { it.copy(email = value) }
    }

    fun onPasswordChange(value: String){
        _uiState.update { it.copy(pass = value) }
    }

    fun login(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errMessage = null) }

            val state = _uiState.value

            val result = repository.login(
                email = state.email,
                password = state.pass
            )

            result
                .onSuccess {
                    _uiState.update {
                        repository.loginSuccess()
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

}