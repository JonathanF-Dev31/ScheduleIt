package com.example.scheduleit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.scheduleit.navigation.AppScreens

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        AppScreens.Progress to Pair(Icons.Filled.Done, "Progress"),
        AppScreens.Schedule to Pair(Icons.Default.DateRange, "Schedule"),
        AppScreens.Home to Pair(Icons.Filled.Home, "Home"),
        AppScreens.Profile to Pair(Icons.Filled.Person, "Profile"),
        AppScreens.Support to Pair(Icons.Filled.Info, "Support")
    )

    NavigationBar(containerColor = Color.White) {
        val currentRoute = navController.currentDestination?.route

        items.forEach { (screen, iconLabelPair) ->
            NavigationBarItem(
                icon = { Icon(iconLabelPair.first, contentDescription = iconLabelPair.second) },
                label = { Text(iconLabelPair.second) },
                selected = currentRoute == screen.route,
                onClick = {
                    println("Navegando a: ${screen.route}")
                    navController.navigate(screen.route) {
                        popUpTo(AppScreens.Home.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

