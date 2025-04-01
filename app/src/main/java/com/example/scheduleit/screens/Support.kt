package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header

@Composable
fun Support(navController: NavController) {
    Scaffold(
        topBar = { Header(name = "Jonathan", date = "23/02/2025", time = "12:26:03 AM") },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        SupportBodyContent(Modifier.padding(paddingValues))
    }
}

@Composable
fun SupportBodyContent(modifier: Modifier = Modifier) {
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

        ChatBubble(text = "Lorem ipsum...", isUser = false, backgroundColor = Color(0xFFD9D9D9))
        ChatBubble(text = "Lorem ipsum...", isUser = true, backgroundColor = Color(0xFF9E99BF))

        Spacer(modifier = Modifier.weight(1f))

        ChatInput { }
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
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color(0xFFD9D9D9))
            }
        }
    )
}


