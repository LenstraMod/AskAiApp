package com.example.chatbot.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatbot.ui.home.chatlogic.ChatViewModel
import com.example.chatbot.ui.setting
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainDrawer(
    navController : NavController,
    viewModel: ChatViewModel,
    content: @Composable (openDrawer: () -> Unit) -> Unit,
){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }

    val session by viewModel.session.collectAsState()

    val itemId by viewModel.editingIemId.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSessions()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet{
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable(
                            onClick = {
                                scope.launch {
                                    drawerState.close()
                                    viewModel.startNewSession()
                                }
                            }
                        )
                ){
                    AppLogo()
                }

                HorizontalDivider()

                Column(Modifier.padding(8.dp)){
                    NavigationDrawerItem(
                        label = { Text("New Chat") },
                        selected = false,
                        onClick = {
                            scope.launch {
                                viewModel.startNewSession()
                                drawerState.close()
                            }
                        },
                    )

                    Text(
                        "Chat History"
                    )

                    session.forEach { session ->

                        val isTheItem = itemId == session.id

                        var tempEditText by remember(itemId, isTheItem) { mutableStateOf(session.title) }

                        NavigationDrawerItem(
                            label = {
                                if(isTheItem){
                                    Row() {
                                        OutlinedTextField(
                                            value = tempEditText,
                                            onValueChange = {tempEditText = it},
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .focusRequester(focusRequester),
                                            singleLine = true,
                                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    viewModel.updateTitle(session.id, tempEditText)
                                                    viewModel.stopEditing()
                                                }
                                            )
                                        )
                                    }
                                }else{
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                          ,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(session.title)


                                    }
                                }
                            },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    viewModel.loadSession(session.id)
                                    drawerState.close()
                                }
                            },
                            badge = {
                                if(isTheItem){
                                    IconButton(
                                        onClick = {
                                            viewModel.updateTitle(session.id, tempEditText)
                                            viewModel.stopEditing()
                                        }
                                    ) {
                                        Icon(Icons.Default.Check, null)
                                    }
                                }else{
                                    Row(
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        IconButton(
                                            onClick = {
                                                viewModel.startEditing(session.id)
                                            }
                                        ) {
                                            Icon(Icons.Default.Edit, null)
                                        }

                                        IconButton(
                                            onClick = {viewModel.deleteSession(session.id)}
                                        ) {
                                            Icon(Icons.Default.Delete, null)
                                        }
                                    }
                                }
                            }
                        )
                    }
                    Spacer(Modifier.weight(1f))

                    NavigationDrawerItem(
                        icon = {
                            Icon(Icons.Default.Settings, "Setting")
                        },
                        label = { Text("Setting") },
                        selected = false,
                        onClick = {
                            scope.launch {
                                navController.navigate(setting)
                                drawerState.close()
                            }
                                  },
                    )
                }
            }
        },
        modifier = Modifier.statusBarsPadding()
    ) {
        content{
            scope.launch {
                drawerState.open()
            }
        }
    }
}