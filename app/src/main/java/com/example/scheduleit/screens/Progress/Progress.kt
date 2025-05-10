package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun Progress(navController: NavController) {
    Scaffold(
        topBar = {
            Header()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
        ) {
            ProgressBodyContent(Modifier)
        }
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
        CircularProgress(percentage = 45, label = "45%")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Total progress", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.TrendingUp, contentDescription = "Advancement", tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Advancement in their levels", fontSize = 16.sp, color = Color.Black)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.TrendingUp, contentDescription = "Advancement", tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Advancement in their levels", fontSize = 16.sp, color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                LevelProgress("Level A1", 100, "72 of 72")
                LevelProgress("Level A2", 100, "72 of 72", Color(0xFFE4B3E5))
                Spacer(modifier = Modifier.height(16.dp))

                LevelProgress("Level A1", 100, "72 of 72", Color(0xFF9B2FAE))
                LevelProgress("Level A2", 80, "60 of 72", Color(0xFFE4B3E5))
                LevelProgress("Level B1", 20, "15 of 72", Color(0xFF6F6693))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CircularProgress(percentage: Int, label: String) {
    // Aquí puedes agregar tu implementación de progreso circular
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Gray, shape = RoundedCornerShape(50))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$percentage%", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun LevelProgress(level: String, progress: Int, classesTaken: String, color: Color = Color(0xFF9B2FAE)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(text = level, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(text = "Classes taken: $classesTaken", fontSize = 14.sp, color = Color.Black)
            }
        }
    }
}
