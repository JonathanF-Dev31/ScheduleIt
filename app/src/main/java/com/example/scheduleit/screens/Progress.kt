package com.example.scheduleit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController


/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun Progress(
    navController: NavController
) {
    Scaffold { paddingValues ->
        ProgressBodyContent(Modifier.padding(paddingValues), navController)
    }
}

@Composable
fun ProgressBodyContent(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Progress Screen")
    }
}