package com.example.scheduleit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xAAFFFFFF),
    loaderColor: Color = Color(0xFF8473A8)
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = loaderColor,
            strokeWidth = 4.dp
        )
    }
}
