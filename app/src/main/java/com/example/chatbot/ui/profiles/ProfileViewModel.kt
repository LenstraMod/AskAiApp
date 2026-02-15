package com.example.chatbot.ui.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository : AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init{
        loadProfile()
    }

    fun loadProfile(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getCurrentUserProfile()
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(
                            username = profile.username,
                            email = profile.email,
                            isLoading = false
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            error = e.message,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun updateUsername(newUsername : String){
        viewModelScope.launch {
            val result = repository.updateProfile(newUsername)

            result.onSuccess {
                _uiState.update {
                    it.copy(
                        username = newUsername
                    )
                }
            }
                .onFailure { e->
                    _uiState.update {
                        it.copy(
                            error = e.message
                        )
                    }
                }
        }
    }
}