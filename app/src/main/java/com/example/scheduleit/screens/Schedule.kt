package com.example.scheduleit.screens

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
import androidx.navigation.NavController
import com.example.scheduleit.components.BottomNavBar
import com.example.scheduleit.components.Header
import java.util.Calendar

@Composable
fun Schedule(navController: NavController) {
    Scaffold(
        topBar = {
            Header(name = "Jonathan", date = "23/02/2025", time = "12:26:03 AM")
        },
        bottomBar = {
            BottomNavBar(navController)
        }
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
            FilterSection()
            Spacer(modifier = Modifier.height(16.dp))
            ClassList()
        }
    }
}

@Composable
fun FilterSection() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OfficeFilterButton()
        DateFilterButton(context)
        HourFilterButton()
    }
}

@Composable
fun OfficeFilterButton() {
    var expanded by remember { mutableStateOf(false) }
    val offices = listOf("Office A", "Office B", "Office C")
    var selectedOffice by remember { mutableStateOf(offices[0]) }

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = selectedOffice, fontSize = 14.sp, color = Color.Black)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            offices.forEach { office ->
                DropdownMenuItem(text = { Text(office) }, onClick = {
                    selectedOffice = office
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun DateFilterButton(context: Context) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var selectedDate by remember { mutableStateOf("Date") }

    val datePickerDialog = DatePickerDialog(
        context, { _, y, m, d -> selectedDate = "$d/${m + 1}/$y" }, year, month, day
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
fun HourFilterButton() {
    var expanded by remember { mutableStateOf(false) }
    val hours = listOf("08:00 AM", "10:00 AM", "02:00 PM", "04:00 PM")
    var selectedHour by remember { mutableStateOf(hours[0]) }

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
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun ClassList() {
    val classes = listOf(
        "CLASS NUMBER 5", "CLASS NUMBER 6", "CLASS NUMBER 7", "CLASS NUMBER 8",
        "CLASS NUMBER 9", "CLASS NUMBER 10", "CLASS NUMBER 11", "CLASS NUMBER 12",
        "CLASS NUMBER 13", "CLASS NUMBER 14", "CLASS NUMBER 15", "CLASS NUMBER 17"
    )
    LazyColumn {
        items(classes) { className ->
            ClassItem(className)
        }
    }
}

@Composable
fun ClassItem(className: String) {
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
        Text(text = "INA1 |", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = className, fontSize = 14.sp, color = Color.Black)
    }
}

