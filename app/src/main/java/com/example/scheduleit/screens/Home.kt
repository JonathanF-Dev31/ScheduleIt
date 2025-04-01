package com.example.scheduleit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.BottomNavBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close




/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = { Header(name = "Jonathan", date = "23/02/2025", time = "12:26:03 AM") },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeBodyContent(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun HomeBodyContent(modifier: Modifier = Modifier) {
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
            Text(text = "Scheduled classes", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        ScheduledClassCard("INA2 Class 8 / Office: Fontibon", "24/09/2025", "08:00 - 09:00", "https://github.com/JonathanF-Dev31")
        ScheduledClassCard("INA2 Exam / Office: Fontibon", "24/09/2025", "07:30 - 08:00", "https://github.com/aleherrera23")
        ScheduledClassCard("INA2 Class 9 / Office: Fontibon", "24/09/2025", "09:00 - 10:00", "https://localhost")
    }
}

@Composable
fun ScheduledClassCard(title: String, date: String, time: String, link: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE4E5F6)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: $date - Time: $time", fontSize = 12.sp, color = Color.Gray)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Remove class",
                    modifier = Modifier
                        .size(24.dp)
                        .offset(y = (0).dp),
                            tint = Color.Black
                )
            }
            Text(text = "Link: $link", fontSize = 12.sp, color = Color.Blue)
        }

    }
}