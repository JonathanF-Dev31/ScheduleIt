package com.example.scheduleit.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.navigation.NavController
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.BottomNavBar


/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun Profile(
    navController: NavController
){
    Scaffold(
        topBar = { Header(name = "Name", date = "01/01/2000", time = "00:00:03 AM") },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        ProfileBodyContent(Modifier.padding(paddingValues), navController)
    }
}

@Composable
fun ProfileBodyContent(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Profile Screen")
    }
}