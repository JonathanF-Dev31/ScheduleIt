package com.example.scheduleit.navigation

/**
 * Created by JonathanDev31 on 19/03/2025
 */

sealed class AppScreens(val route: String) {
    object Home: AppScreens(route = "home")
    object Login: AppScreens(route = "login")
    object Profile: AppScreens(route ="profile")
    object Progress: AppScreens(route ="progress")
    object Schedule: AppScreens(route ="schedule")
    object Support: AppScreens(route ="support")
}