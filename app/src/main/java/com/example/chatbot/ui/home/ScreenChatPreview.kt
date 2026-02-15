package com.example.chatbot.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatbot.ui.components.InputBar
import com.example.chatbot.ui.components.MessageList
import com.example.chatbot.ui.home.chatlogic.ChatViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ScreenChatPreview(
    viewModel: ChatViewModel
){

    LaunchedEffect(Unit) {
        Log.d("ChatVM", "Screen observing VM: ${viewModel.hashCode()}")
    }

    val state by viewModel.uiState.collectAsState()

    var text by remember { mutableStateOf("") }


    Column(Modifier.fillMaxSize()){

        state.error?.let{
            Text(
                text = it,
                color = Color.Red
            )
        }
        MessageList(state.messages, Modifier.weight(1f))

        if(state.isLoading){
            CircularProgressIndicator()
        }

        InputBar(
            text = text,
            onTextChange = { text = it},
            onSend = {
                if(text.isNotBlank()){
                    viewModel.sendUserMessage(text)
                    text = ""
                }
            }
        )
    }
}