package com.example.scheduleit.screens.schedule

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import java.util.Calendar
import com.example.scheduleit.components.LoadScreen

@Composable
fun Schedule(navController: NavController, viewModel: ScheduleViewModel = viewModel()) {

    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LoadScreen(modifier = Modifier.fillMaxSize())
        }

        if (!isLoading) {
            Scaffold(
                topBar = { Header() },
                bottomBar = { BottomNavBar(navController) }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(paddingValues)
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
                    FilterSection(viewModel)
                    Spacer(modifier = Modifier.height(16.dp))
                    ClassList(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun FilterSection(viewModel: ScheduleViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DateFilterButton(context, viewModel)
        HourFilterButton(viewModel)
    }
}

@Composable
fun DateFilterButton(context: Context, viewModel: ScheduleViewModel) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDate by remember { mutableStateOf(viewModel.dateFilter.value) }

    val datePickerDialog = DatePickerDialog(
        context, { _, y, m, d -> selectedDate = "$d/${m + 1}/$y"; viewModel.setDateFilter(selectedDate) },
        year, month, day
    )

    Button(
        onClick = { datePickerDialog.show() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = selectedDate, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun HourFilterButton(viewModel: ScheduleViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val hours = listOf("08:00 AM", "10:00 AM", "02:00 PM", "04:00 PM")
    var selectedHour by remember { mutableStateOf(viewModel.hourFilter.value) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = selectedHour, fontSize = 14.sp, color = Color.Black)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            hours.forEach { hour ->
                DropdownMenuItem(text = { Text(hour) }, onClick = {
                    selectedHour = hour
                    viewModel.setHourFilter(hour)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun ClassList(viewModel: ScheduleViewModel = viewModel()) {
    val classes by viewModel.completedClasses.collectAsState()

    LazyColumn {
        items(classes) { classItem ->
            ClassItem(className = classItem.title.toString(), classLevel = classItem.level.toString())
        }
    }
}



@Composable
fun ClassItem(className: String, classLevel: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(vertical = 2.dp)
            .background(Color(0xFFDCDCDC), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = false,
            onCheckedChange = {},
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF74708C), uncheckedColor = Color(0xFF74708C))
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "IN$classLevel |", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = className, fontSize = 14.sp, color = Color.Black)
    }
}