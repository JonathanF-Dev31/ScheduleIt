package com.example.scheduleit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.scheduleit.navigation.AppNavigation
import com.example.scheduleit.ui.theme.ScheduleItTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScheduleItTheme {
                AppNavigation()
            }
        }
    }
}