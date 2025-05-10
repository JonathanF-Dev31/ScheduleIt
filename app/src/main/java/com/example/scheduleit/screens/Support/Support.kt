package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.LoadScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SupportViewModel : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<String>>(emptyList())
    val chatMessages: StateFlow<List<String>> get() = _chatMessages

    fun sendMessage(message: String) {
        _chatMessages.value = _chatMessages.value + message
    }
}

@Composable
fun Support(navController: NavController) {
    val viewModel: SupportViewModel = viewModel()

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        delay(500)
        isLoading = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LoadScreen(modifier = Modifier.fillMaxSize())
        } else {
            Scaffold(
                topBar = { Header() },
                bottomBar = { BottomNavBar(navController) }
            ) { paddingValues ->
                SupportBodyContent(modifier = Modifier.padding(paddingValues), viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SupportBodyContent(modifier: Modifier = Modifier, viewModel: SupportViewModel) {
    val chatMessages by viewModel.chatMessages.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8473A8)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Support chat", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        chatMessages.forEachIndexed { index, message ->
            ChatBubble(
                text = message,
                isUser = index % 2 != 0,
                backgroundColor = if (index % 2 == 0) Color(0xFFD9D9D9) else Color(0xFF9E99BF)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        ChatInput { message ->
            if (message.isNotBlank()) {
                viewModel.sendMessage(message)
            }
        }
    }
}

@Composable
fun ChatBubble(text: String, isUser: Boolean, backgroundColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
        ) {
            Text(text = text, color = Color.Black, fontSize = 14.sp)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ChatInput(onMessageSend: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Type your message", color = Color(0xFF54505A)) },
        textStyle = TextStyle(color = Color(0xFF8B8B8B)),
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotBlank()) {
                    onMessageSend(text)
                    text = ""
                }
            }) {
                Icon(Icons.Filled.Send, contentDescription = "Send", tint = Color(0xFFD9D9D9))
            }
        }
    )
}
