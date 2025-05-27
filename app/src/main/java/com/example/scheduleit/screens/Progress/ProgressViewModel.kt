package com.example.scheduleit.screens.Progress

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleit.screens.home.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.collections.iterator

class ProgressViewModel : ViewModel() {

    private val totalClassesByLevel = mapOf(
        "A1" to 4,
        "A2" to 3,
        "B1" to 2,
        "B2" to 2,
        "C1" to 2
    )

    val levelProgress = mutableStateOf<Map<String, Int>>(emptyMap())
    val levelClassesTaken = mutableStateOf<Map<String, String>>(emptyMap())
    val isLoading = mutableStateOf(true)

    init {
        // Datos estáticos simulados
        simulateProgress()
    }

    private fun simulateProgress() {
        // Simula que se tomaron 2 clases en A1
        val completedClasses = listOf("A1 Class 1", "A1 Class 2")
        val progressData = calculateProgressFromCompleted(completedClasses)
        levelProgress.value = progressData.first
        levelClassesTaken.value = progressData.second
        isLoading.value = false
    }

    private fun calculateProgressFromCompleted(completedClasses: List<String>): Pair<Map<String, Int>, Map<String, String>> {
        val levelProgressMap = mutableMapOf<String, Int>()
        val classesTakenMap = mutableMapOf<String, String>()

        val completedByLevel = mutableMapOf<String, Int>()
        completedClasses.forEach { title ->
            val level = title.substringBefore(" ").uppercase()
            completedByLevel[level] = (completedByLevel[level] ?: 0) + 1
        }

        for ((level, total) in totalClassesByLevel) {
            val completed = completedByLevel[level] ?: 0
            val percentage = (completed.toFloat() / total * 100).toInt()
            levelProgressMap[level] = percentage
            classesTakenMap[level] = "$completed of $total"
        }

        return Pair(levelProgressMap, classesTakenMap)
    }
}
