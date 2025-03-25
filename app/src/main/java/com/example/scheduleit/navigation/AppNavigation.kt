package com.example.scheduleit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scheduleit.screens.Home
import com.example.scheduleit.screens.Login
import com.example.scheduleit.screens.Profile
import com.example.scheduleit.screens.Progress
import com.example.scheduleit.screens.Schedule
import com.example.scheduleit.screens.Support
import com.example.scheduleit.components.BottomNavBar

/**
 * Created by JonathanDev31 on 19/03/2025
 */
@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Home.route){
        composable(route = AppScreens.Home.route){
            Home(navController)
        }
        composable(route = AppScreens.Login.route){
            Login(navController)
        }
        composable(route = AppScreens.Profile.route){
            Profile(navController)
        }
        composable(route = AppScreens.Progress.route){
            Progress(navController)
        }
        composable(route = AppScreens.Schedule.route){
            Schedule(navController)
        }
        composable(route = AppScreens.Support.route){
            Support(navController)
        }

    }
}