package com.example.chatbot.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val repository : AuthRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    init{
        loadProfile()
    }

    fun loadProfile(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getCurrentUserProfile()
                .onSuccess { state ->
                    _uiState.update {
                        it.copy(
                            username = state.username,
                            email = state.email,
                            isLoading = false
                        )
                    }
                }
                .onFailure { e->
                    _uiState.update {
                        it.copy(
                            error = e.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun logout(){
        viewModelScope.launch {

            try {
                _uiState.update {
                    it.copy(
                        isLoading = true
                    )
                }

                repository.logout()

            }catch (e : Exception){
                _uiState.update {
                    it.copy(
                        error = e.message
                    )
                }
            }

        }
    }

}