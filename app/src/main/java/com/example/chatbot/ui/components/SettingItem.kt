package com.example.chatbot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingItem(
    icon : ImageVector,
    title : String,
    isDestructive : Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    val color = if (isDestructive) Color.Red else Color.DarkGray

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable{ onClick() },
        horizontalArrangement = Arrangement
            .spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = title,
            style = TextStyle(
                color = color,
                fontSize = 16.sp
            )
        )
    }
}