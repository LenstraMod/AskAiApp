package com.example.chatbot.ui.home.chatlogic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatbot.data.repository.ChatHistoryRepository
import com.example.chatbot.data.repository.ChatRepository
import com.example.chatbot.ui.home.UiMessage
import com.example.chatbot.ui.home.chatsession.ChatSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository,
    private val history: ChatHistoryRepository
) : ViewModel(){

    private var currentSessionId: String? = null

    private val _session = MutableStateFlow<List<ChatSession>>(emptyList())
    val session = _session.asStateFlow()

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private val _editingItemId= MutableStateFlow<String?>(null)
    val editingIemId = _editingItemId.asStateFlow()

    fun sendUserMessage(text: String){

        viewModelScope.launch {

        val sessionId = currentSessionId ?: history.createSession().also {
            currentSessionId = it
        }

        _uiState.update { state ->
            state.copy(
                messages = state.messages + UiMessage(text, true),
                isLoading = true,
                error = null
            )
        }

            try {

                history.saveMessage(sessionId, UiMessage(text,true))

                val replyText = repository.sendtoAI(text)

                history.saveMessage(sessionId, UiMessage(replyText, false))
                _uiState.update {
                    it.copy(
                        messages = it.messages + UiMessage(replyText, false),
                        isLoading = false
                    )
                }
            }catch (e : Exception){
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun startNewSession(){
        viewModelScope.launch {
            currentSessionId = history.createSession()
            _uiState.value = ChatUiState()
            _session.value = history.getSession()
        }
    }

    fun loadSession(sessionId: String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            currentSessionId = sessionId

            try{
                val messages = history.loadMessage(sessionId)

                _uiState.update {
                    it.copy(
                        messages = messages,
                        isLoading = false,
                        error = null
                    )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(
                        error = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    suspend fun loadSessionsInternal(){
        _session.value = history.getSession()
    }

    fun loadSessions(){
        viewModelScope.launch {
           try{
               loadSessionsInternal()
           } catch (e: Exception){
               Log.d("ChatVM","No User, skip load sessions")
               _session.value = emptyList()
           }
        }
    }

    fun clearState(){
        currentSessionId = null
        _session.value = emptyList()
        _uiState.value = ChatUiState()
    }

    fun updateTitle(id: String, newTitle: String){
        viewModelScope.launch {
            try {
                history.updateTitle(id, newTitle)

                _session.value = history.getSession()
            }catch (e : Exception){
                Log.e("ChatVM","Failed to update title: ${e.message}")
            }
        }
    }

    fun startEditing(id : String){
        _editingItemId.value = id
    }

    fun stopEditing(){
        _editingItemId.value = null
    }

    fun deleteSession(sessionId: String){
        viewModelScope.launch {
            try{
                history.deleteSession(sessionId)

                //Remove from local list
                _session.update { list ->
                    list.filterNot { it.id == sessionId }
                }

                //Make the screen blank again
                if(currentSessionId == sessionId){
                    currentSessionId = null
                    _uiState.value = ChatUiState()
                }
            }catch (e : Exception){
                Log.d("CHATVM","Error when deleting session: ${e.message}")
            }
        }
    }
}
