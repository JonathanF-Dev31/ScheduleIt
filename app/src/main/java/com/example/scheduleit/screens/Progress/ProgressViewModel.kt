package com.example.scheduleit.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay


class ProgressViewModel : ViewModel() {

    private val courseData = mapOf(
        "A1" to Pair(72, 72),
        "A2" to Pair(72, 60),
        "B1" to Pair(72, 15)
    )

    val levelProgress = mutableStateOf<Map<String, Int>>(emptyMap())
    val levelClassesTaken = mutableStateOf<Map<String, String>>(emptyMap())
    val isLoading = mutableStateOf(true)

    init {
        fetchProgress()
    }

    private fun fetchProgress() {
        viewModelScope.launch {
            delay(600)
            val progressData = calculateProgress()
            withContext(Dispatchers.Main) {
                levelProgress.value = progressData.first
                levelClassesTaken.value = progressData.second
                isLoading.value = false
            }
        }
    }

    private fun calculateProgress(): Pair<Map<String, Int>, Map<String, String>> {
        val progress = mutableMapOf<String, Int>()
        val classesTaken = mutableMapOf<String, String>()

        for ((level, pair) in courseData) {
            val total = pair.first
            val completed = pair.second
            val percentage = (completed.toFloat() / total * 100).toInt()
            progress[level] = percentage
            classesTaken[level] = "$completed of $total"
        }

        return Pair(progress, classesTaken)
    }
}
