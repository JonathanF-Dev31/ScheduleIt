package com.example.scheduleit.screens.home

import androidx.compose.foundation.background
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
import com.example.scheduleit.models.Class
import com.example.scheduleit.models.User


/**
 * Created by JonathanDev31 on 19/03/2025
 */

@Composable
fun Home(navController: NavController, viewModel: HomeViewModel = HomeViewModel()) {

    Scaffold(
        topBar = { Header() },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeBodyContent(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun HomeBodyContent(modifier: Modifier = Modifier) {

}
