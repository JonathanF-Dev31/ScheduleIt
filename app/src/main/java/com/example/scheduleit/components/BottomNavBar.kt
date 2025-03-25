package com.example.scheduleit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.scheduleit.navigation.AppScreens

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        AppScreens.Home to Icons.Filled.Home,
        AppScreens.Schedule to Icons.Default.DateRange,
        AppScreens.Profile to Icons.Filled.Person,
        AppScreens.Support to Icons.Filled.Info
    )

    NavigationBar(containerColor = Color.White) {
        val currentRoute = navController.currentDestination?.route

        items.forEach { (screen, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = null) },
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

