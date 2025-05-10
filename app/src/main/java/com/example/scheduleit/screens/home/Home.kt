package com.example.scheduleit.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.BottomNavBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scheduleit.components.LoadScreen

/**
 * Created by JonathanDev31 on 19/03/2025
 */

@Composable
fun Home(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(500)
        isLoading = false
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        if (isLoading) {
            LoadScreen(modifier = Modifier.fillMaxSize())
        }

        if (!isLoading) {
            Scaffold(
                topBar = { Header() },
                bottomBar = { BottomNavBar(navController) }
            ) { paddingValues ->
                HomeBodyContent(modifier = Modifier
                    .padding(paddingValues)
                    .background(Color.White)
                )
            }
        }
    }
}



@Composable
fun HomeBodyContent(modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {

    val classes by viewModel.classes.collectAsState()

    Log.d("HomeBodyContent", "Classes size: ${classes.size}")

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

        if (classes.isEmpty()) {
            Text(text = "No classes available")
        } else {
            LazyColumn {
                items(classes) { classItem ->
                    ScheduledClassCard(
                        classId = classItem.title ?: return@items,
                        title = classItem.description ?: "No title",
                        date = classItem.date ?: "No date",
                        time = classItem.time ?: "No time",
                        link = classItem.link ?: "No link",
                        onRemove = { classId -> viewModel.removeClassFromUserSchedule(classId) }
                    )
                }
            }

        }
    }
}


@Composable
fun ScheduledClassCard(
    classId: String,
    title: String,
    date: String,
    time: String,
    link: String,
    onRemove: (String) -> Unit
) {
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
                        .clickable { onRemove(classId) },
                    tint = Color.Black
                )
            }
            Text(text = "Link: $link", fontSize = 12.sp, color = Color.Blue)
        }

    }
}
