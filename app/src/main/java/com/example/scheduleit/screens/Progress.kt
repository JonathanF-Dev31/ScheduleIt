package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.getCurrentDateTime
import com.example.scheduleit.components.fetchUserName


@Composable
fun Progress(navController: NavController) {
    var name by remember { mutableStateOf("Usuario") }
    val (date, time) = getCurrentDateTime()

    LaunchedEffect(Unit) {
        fetchUserName { userName -> name = userName }
    }

    Scaffold(
        topBar = {
            Header(name = name, date = date, time = time)
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { paddingValues ->
        ProgressBodyContent(
            Modifier
                .padding(paddingValues)
                .background(Color.White)
        )
    }
}


@Composable
fun ProgressBodyContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8473A8)),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Your progress", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgress(percentage = 45, label = "45%")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Total progress", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "Advancement", tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Advancement in their levels", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ){
            Column {
                LevelProgress("Level A1", 100, "72 of 72")
                LevelProgress("Level A2", 100, "72 of 72", Color(0xFFE4B3E5))
                LevelProgress("Level B1", 20, "15 of 72", Color(0xFF6F6693))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun CircularProgress(percentage: Int, label: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(140.dp)
    ) {
        CircularProgressIndicator(
            progress = { percentage / 100f },
            color = Color(0xFF9B2FAE),
            trackColor = Color(0xFF9E99BF),
            strokeWidth = 20.dp,
            modifier = Modifier.size(140.dp)
        )
        Text(text = label, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun LevelProgress(level: String, progress: Int, classesTaken: String, color: Color = Color(0xFF9B2FAE)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            CircularProgressIndicator(
                progress = { progress / 100f },
                color = color,
                trackColor = Color(0xFF9E99BF),
                strokeWidth = 18.dp,
                modifier = Modifier.size(120.dp)
            )
            Text(text = "$progress%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = level, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Classes taken: $classesTaken", fontSize = 14.sp, color = Color.Black)
            }
        }
}