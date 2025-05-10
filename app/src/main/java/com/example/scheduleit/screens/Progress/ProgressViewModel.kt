package com.example.scheduleit.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProgressViewModel : ViewModel() {

    private val courseData = mapOf(
        "A1" to Pair(4, 4),
        "A2" to Pair(3, 3),
        "B1" to Pair(1, 1)
    )

    val levelProgress = mutableStateOf<Map<String, Int>>(emptyMap())

    val isLoading = mutableStateOf(false)

    init {
        fetchProgress()
    }

    private fun fetchProgress() {
        viewModelScope.launch {
            isLoading.value = true
            val progressData = calculateProgress()
            withContext(Dispatchers.Main) {
                levelProgress.value = progressData
                isLoading.value = false
            }
        }
    }

    private fun calculateProgress(): Map<String, Int> {
        return courseData.mapValues { entry ->
            val totalClasses = entry.value.first
            val completedClasses = entry.value.second
            (completedClasses.toFloat() / totalClasses * 100).toInt()
        }
    }
}