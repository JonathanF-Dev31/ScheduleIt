package com.example.scheduleit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding

@Composable
fun Header(name: String, date: String = "", time: String = "", isLoginScreen: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF9E99BF))
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoginScreen) {
            Text(
                text = "HI! Student",
                fontSize = 20.sp,
                color = Color.White
            )
            HorizontalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "This is your App to schedule classes",
                fontSize = 12.sp,
                color = Color.White
            )
        } else {
            Text(
                text = "HI! $name",
                fontSize = 20.sp,
                color = Color.White
            )
            HorizontalDivider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Date of access: $date - Time: $time",
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}

