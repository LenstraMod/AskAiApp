package com.example.chatbot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatbot.di.OpenRouterModule
import com.example.chatbot.di.firebaseModule
import com.example.chatbot.di.repositoryModule
import com.example.chatbot.di.viewModelModule
import com.example.chatbot.ui.NavApp
import com.example.chatbot.ui.auth.LoginScreen
import com.example.chatbot.ui.theme.ChatBotTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)

            modules(
                firebaseModule,
                repositoryModule,
                viewModelModule,
                OpenRouterModule
            )
        }

        setContent {
            ChatBotTheme {
                NavApp()
            }
        }
    }
}