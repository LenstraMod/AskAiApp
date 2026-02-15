package com.example.chatbot.ui.auth

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatbot.ui.Login
import com.example.chatbot.ui.Register
import com.example.chatbot.ui.auth.registerlogic.RegisterViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = koinViewModel(),
){

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if(state.isSuccess){
            navController.navigate(Login){
                popUpTo(Register){ inclusive = true }
                viewModel.consumeSuccess()
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    )
    { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ASK",
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFfb542b)
                    )
                )
                Text(
                    text = " AI",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Black,

                    )

                )
            }

            Spacer(Modifier.height(64.dp))

           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(12.dp)
           ) {
               Text(
                   text = "Sign Up to your Account",
                   style = TextStyle(
                       fontSize = 16.sp,
                       fontWeight = FontWeight.Medium,
                       fontFamily = FontFamily.SansSerif,
                       color = Color(0xFFfb542b)
                   ),
                   modifier = Modifier
                       .fillMaxWidth()
               )

               Spacer(Modifier.height(16.dp))

               OutlinedTextField(
                   value = state.username,
                   onValueChange = viewModel::onUsernameChange,
                   label = {Text("Username")},
                   modifier = Modifier.fillMaxWidth(),
                   singleLine = true,
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
               )

               Spacer(Modifier.height(12.dp))

               OutlinedTextField(
                   value = state.email,
                   onValueChange = viewModel::onEmailChange,
                   label = {Text("Email")},
                   modifier = Modifier.fillMaxWidth(),
                   singleLine = true,
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
               )

               Spacer(Modifier.height(12.dp))

               OutlinedTextField(
                   value = state.password,
                   onValueChange = viewModel::onPasswordChange,
                   label = {Text("Password")},
                   modifier = Modifier.fillMaxWidth(),
                   singleLine = true,
                   visualTransformation = PasswordVisualTransformation(),
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
               )
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(4.dp)
               ) {
                   Text(
                       text = "Already have an account ?",
                       style = TextStyle(
                           fontWeight = FontWeight.Medium,
                           fontFamily = FontFamily.SansSerif
                       )

                   )
                   Text(
                       text = " Sign In Here!",
                       style = TextStyle(
                           color = Color(0xFFfb542b),
                           fontWeight = FontWeight.Medium,
                           fontFamily = FontFamily.SansSerif
                       ),
                       modifier = Modifier.clickable(
                           onClick = {
                               navController.navigate(Login)
                           }
                       )
                   )
               }
               Spacer(Modifier.height(48.dp))
               Button(
                   onClick = {
                       viewModel.register()
                   },
                   modifier = Modifier.fillMaxWidth(),
                   colors = ButtonDefaults.buttonColors(
                       containerColor = Color(0xFFfb542b)
                   ),
                   shape = RoundedCornerShape(12.dp)
               ) {
                   Text(
                       text = "Sign Up",
                       style = TextStyle(
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold,
                           fontFamily = FontFamily.SansSerif,
                           color = Color.White
                       )
                   )
               }


               state.errMessage?.let {
                   Text(it)
               }

               if(state.isLoading){
                   CircularProgressIndicator()
               }

           }
        }
    }
}