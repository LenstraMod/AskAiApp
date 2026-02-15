package com.example.chatbot.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.chatbot.data.auth.AuthRepository
import com.example.chatbot.ui.auth.LoginScreen
import com.example.chatbot.ui.auth.RegisterScreen
import com.example.chatbot.ui.components.MainDrawer
import com.example.chatbot.ui.home.HomeScreen
import com.example.chatbot.ui.home.chatlogic.ChatViewModel
import com.example.chatbot.ui.profiles.ProfileScreen
import com.example.chatbot.ui.settings.SettingScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel


@Composable
fun NavApp(viewModel: MainViewModel = koinViewModel(), chatViewModel: ChatViewModel = koinViewModel()){
    val navController = rememberNavController()
    val loggedIn by viewModel.isLoggedIn.collectAsState()

    val auth = FirebaseAuth.getInstance()
    DisposableEffect(auth) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                chatViewModel.clearState()
                chatViewModel.loadSessions()
            } else {
                chatViewModel.clearState()
            }
        }

        auth.addAuthStateListener(listener)

        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    MainDrawer(navController, chatViewModel) { openDrawer ->
        NavHost(
            navController = navController,
            startDestination = if (loggedIn) Home else Login
        ){
            composable<Login>{
                LoginScreen(navController)
            }

            composable<Register> {
                RegisterScreen(navController)
            }

            composable<Home> {
                HomeScreen(openDrawer, chatViewModel)
            }

            composable<profile> {
                ProfileScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            composable<setting> {
                SettingScreen(
                    onSuccessBack = {
                        navController.navigate(Login){
                            popUpTo(setting){ inclusive=true }
                        }
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    goToEditProfile = {
                        navController.navigate(profile)
                    }
                )
            }
        }
    }
}