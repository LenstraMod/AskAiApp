package com.example.chatbot.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatbot.ui.home.UiMessage

@Composable
fun MessageList(
    messages: List<UiMessage>,
    modifier: Modifier = Modifier
    ){
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        items(messages){ msg ->
            MessageBubble(msg)
        }
    }
}