package com.example.chatbot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.chatbot.ui.home.UiMessage

@Composable
fun MessageBubble(msg : UiMessage){
    val alignment =
        if(msg.isUser){
            Alignment.CenterEnd
        }else{
            Alignment.CenterStart
        }

    val color =
        if(msg.isUser){
            Color(0xFFDCF8C6)
        }else{
            Color(0xFFFFFFFF)
        }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = alignment
    ){
        Text(
            text = msg.text,
            modifier = Modifier
                .padding(6.dp)
                .background(color, RoundedCornerShape(12.dp))
                .padding(10.dp)
        )
    }
}