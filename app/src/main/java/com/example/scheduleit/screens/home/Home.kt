package com.example.scheduleit.screens.home

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.LoadScreen
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.Duration
import android.content.Context

/**
 * Created by JonathanDev31 on 19/03/2025
 */

@RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
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
            val context = LocalContext.current
            LazyColumn {
                itemsIndexed(classes) { index, classItem ->
                    val isLast = index == classes.lastIndex

                    ScheduledClassCard(
                        classId = classItem.title ?: return@itemsIndexed,
                        title = classItem.title,
                        level = classItem.level ?: "No level",
                        date = classItem.date ?: "No date",
                        time = classItem.time ?: "No time",
                        link = classItem.link ?: "No link",
                        isLast = isLast,
                        context = context,
                        onRemove = { classId ->
                            if (isLast) {
                                viewModel.removeClassFromUserSchedule(classId)
                            } else {
                                Toast.makeText(
                                    context,
                                    "You must cancel classes in order",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            }


        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduledClassCard(
    classId: String,
    title: String,
    level: String,
    date: String,
    time: String,
    link: String,
    isLast: Boolean,
    context: Context,
    onRemove: (String) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("d/M/yyyy h:mm a")
    val cleanTime = time.trim().removePrefix("0")

    val classDateTime = try {
        LocalDateTime.parse("$date $cleanTime", formatter)
    } catch (e: Exception) {
        Log.e("ParseError", "Error parsing date time: ${e.message}")
        null
    }

    val now = LocalDateTime.now(ZoneId.systemDefault())
    val diffHours = classDateTime?.let { Duration.between(now, it).toHours() }

    Log.d("ScheduledClassCard", "Now: $now, Class: $classDateTime, Diff: $diffHours")

    val canCancel = (diffHours ?: -1) > 6

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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$title | $level",
                    fontSize = 16.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = if (canCancel) "Remove class" else "Cannot cancel",
                    modifier = Modifier
                        .size(24.dp)
                        .then(
                            if (canCancel) Modifier.clickable { onRemove(classId) }
                            else Modifier
                        ),
                    tint = if (canCancel) Color.Black else Color.Gray
                )
            }

            Text(
                text = "Link: $link",
                fontSize = 12.sp,
                color = Color.Blue
            )

            if (!canCancel) {
                Text(
                    text = "Cannot cancel class within 6 hours of start",
                    fontSize = 10.sp,
                    color = Color.Red
                )
            }
        }
    }
}