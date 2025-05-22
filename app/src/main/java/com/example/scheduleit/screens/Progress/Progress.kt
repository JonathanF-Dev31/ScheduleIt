package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header


@Composable
fun Progress(navController: NavController, viewModel: ProgressViewModel = viewModel()) {
    val isLoading by viewModel.isLoading
    val levelProgress by viewModel.levelProgress
    val levelClassesTaken by viewModel.levelClassesTaken

    Scaffold(
        topBar = { Header() },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFB400CC))
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8473A8)),
                        shape = RoundedCornerShape(32.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Class Scheduler", color = Color.White, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val totalProgress = if (levelProgress.isNotEmpty())
                        levelProgress.values.sum() / levelProgress.size
                    else 0

                    OverallProgressIndicator(totalProgress)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Progress by level",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de niveles
                    levelProgress.forEach { (level, progress) ->
                        val classes = levelClassesTaken[level] ?: ""
                        LevelProgress(level, progress, classes)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun OverallProgressIndicator(progressPercent: Int) {
    val progress = progressPercent / 100f
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(180.dp)
    ) {
        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 12.dp,
            color = Color(0xFFB400CC),
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = "$progressPercent%",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
    }
}

@Composable
fun LevelProgress(level: String, progress: Int, takenText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(80.dp)
        ) {
            CircularProgressIndicator(
                progress = { progress / 100f },
                strokeWidth = 8.dp,
                color = Color(0xFF8473A8),
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "$progress%",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text("Level $level", fontSize = 16.sp, color = Color.Black)
            Text("Classes taken: $takenText", fontSize = 14.sp, color = Color.Black)
        }
    }
}
