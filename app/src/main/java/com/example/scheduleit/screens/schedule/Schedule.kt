package com.example.scheduleit.screens.schedule

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
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
import com.example.scheduleit.models.Class
import com.example.scheduleit.components.LoadScreen
import androidx.compose.foundation.lazy.itemsIndexed


@Composable
fun Schedule(navController: NavController, viewModel: ScheduleViewModel = viewModel()) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(400)
        isLoading = false
    }

    if (isLoading) {
        LoadScreen(modifier = Modifier.fillMaxSize())
    } else {
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
                    FilterSection(viewModel)
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .height (400.dp)
                            .fillMaxWidth()
                    ) {
                        ClassList(viewModel = viewModel)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .height (100.dp)
                            .fillMaxWidth()
                    ) {
                        ConfirmSelectionButton(viewModel = viewModel)
                    }
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

    // Crear el diálogo
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, y, m, d ->
                val selectedCal = Calendar.getInstance().apply {
                    set(y, m, d)
                }
                val dayOfWeek = selectedCal.get(Calendar.DAY_OF_WEEK)
                if (dayOfWeek in Calendar.MONDAY..Calendar.FRIDAY) {
                    selectedDate = "$d/${m + 1}/$y"
                    viewModel.setDateFilter(selectedDate)
                } else {
                    Toast.makeText(context, "Select a weekday (Mon–Fri)", Toast.LENGTH_SHORT).show()
                }
            },
            year, month, day
        ).apply {
            datePicker.minDate = calendar.timeInMillis
        }
    }

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
    val selectedDate = viewModel.dateFilter.value
    val today = remember {
        val calendar = Calendar.getInstance()
        "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
    }

    val currentHour = remember {
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    }

    val isToday = selectedDate == today

    val hours = remember(selectedDate) {
        (8..20 step 2)
            .filter { hour -> !isToday || hour > currentHour }
            .map { hour ->
                val isPM = hour >= 12
                val displayHour = if (hour == 12 || hour == 0) 12 else hour % 12
                val period = if (isPM) "PM" else "AM"
                String.format("%02d:00 %s", displayHour, period)
            }
    }

    var expanded by remember { mutableStateOf(false) }
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
            if (hours.isEmpty()) {
                DropdownMenuItem(text = { Text("No available hours") }, onClick = {})
            } else {
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
}

@Composable
fun ClassList(viewModel: ScheduleViewModel = viewModel()) {
    val classes by viewModel.completedClasses.collectAsState()
    LazyColumn {
        itemsIndexed(classes) { index, classItem ->
            ClassItem(
                classItem = classItem,
                isFirst = index == 0,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ClassItem(
    classItem: Class,
    isFirst: Boolean,
    viewModel: ScheduleViewModel = viewModel()
) {
    val classSelections = viewModel.classSelections.collectAsState().value
    val isChecked = classSelections[classItem.id] ?: false

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
            checked = isChecked,
            onCheckedChange = { checked ->
                viewModel.toggleClassSelection(classItem.id!!, checked)
            },
            enabled = isFirst,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF8473A8),
                uncheckedColor = Color(0xFF74708C),
                disabledCheckedColor = Color.LightGray,
                disabledUncheckedColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = "IN ${classItem.level} |", fontSize = 14.sp, color = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = classItem.title.toString(), fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun ConfirmSelectionButton(viewModel: ScheduleViewModel) {
    val context = LocalContext.current

    val classSelections by viewModel.classSelections.collectAsState()
    val allClasses by viewModel.classes.collectAsState()

    val selectedClassObjects = allClasses
        .filter { classSelections[it.id] == true }
        .take(1)

    val selectedDate = viewModel.dateFilter.value
    val selectedHour = viewModel.hourFilter.value

    val isButtonEnabled = selectedDate != "Date" && selectedHour != "Time" && selectedClassObjects.isNotEmpty()

    Button(
        onClick = {
            val userEmail = viewModel.user.value?.email ?: return@Button
            selectedClassObjects.forEach { classItem ->
                viewModel.addClassToSchedule(userEmail, classItem, selectedDate, selectedHour)
            }
        },
        enabled = isButtonEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isButtonEnabled) Color(0xFF8473A8) else Color.Gray
        ),
        shape = RoundedCornerShape(32.dp)
    ) {
        Text(
            text = "Confirm Selection",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}