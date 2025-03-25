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
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.scheduleit.navigation.AppScreens
import com.example.scheduleit.components.Header
import com.example.scheduleit.components.BottomNavBar


/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun Home(navController: NavController) {
    Scaffold(
        topBar = { Header(name = "Name", date = "01/01/2000", time = "00:00:03 AM") },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        HomeBodyContent(Modifier.padding(paddingValues), navController)
    }
}

@Composable
fun HomeBodyContent(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Home Screen")

        Button(
            onClick = {
                navController.navigate(route = AppScreens.Support.route)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Go to Support")
        }
    }
}