package com.example.scheduleit.navigation

/**
 * Created by JonathanDev31 on 19/03/2025
 */

sealed class AppScreens(val route: String) {
    object Home: AppScreens("home_screen")
    object Login: AppScreens("login_screen")
    object Profile: AppScreens("profile_screen")
    object Progress: AppScreens("progress_screen")
    object Schedule: AppScreens("schedule_screen")
    object Support: AppScreens("support_screen")
}