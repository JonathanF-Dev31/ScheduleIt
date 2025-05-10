package com.example.scheduleit.screens.schedule

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.scheduleit.models.Class
import com.example.scheduleit.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.State

class ScheduleViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _dateFilter = mutableStateOf("Date")
    val dateFilter: State<String> = _dateFilter

    private val _hourFilter = mutableStateOf("Time")
    val hourFilter: State<String> = _hourFilter

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user : StateFlow<User?> = _user

    private val _completedClasses: MutableStateFlow<List<Class>> = MutableStateFlow(emptyList())
    val completedClasses: StateFlow<List<Class>> = _completedClasses

    // Estado de carga
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getUser()
    }

    private fun getUser() {
        val userEmail = auth.currentUser?.email ?: return

        db.collection("users").document(userEmail)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("HomeViewModel", "Error escuchando cambios en el usuario", e)
                    return@addSnapshotListener
                }

                val user = snapshot?.toObject(User::class.java)
                _user.value = user

                user?.email?.let { email ->
                    getAllCompletedClasses(email)
                }
            }
    }

    fun getAllCompletedClasses(userEmail: String) {
        _isLoading.value = true // Inicia la carga

        db.collection("users")
            .document(userEmail)
            .get()
            .addOnSuccessListener { userSnapshot ->
                val completedClasses = userSnapshot.get("completedClasses") as? List<String> ?: emptyList()

                db.collection("class")
                    .get()
                    .addOnSuccessListener { classSnapshot ->
                        val allClasses = classSnapshot.documents

                        val notCompletedClasses = allClasses.filter { classDoc ->
                            val classTitle = classDoc.id
                            !completedClasses.contains(classTitle)
                        }

                        val remainingClasses = notCompletedClasses.mapNotNull { doc ->
                            doc.toObject(Class::class.java)
                        }

                        _completedClasses.value = remainingClasses
                        _isLoading.value = false // Termina la carga
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error obteniendo clases disponibles", e)
                        _isLoading.value = false // Termina la carga en caso de error
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error obteniendo datos del usuario", e)
                _isLoading.value = false // Termina la carga en caso de error
            }
    }

    fun setDateFilter(date: String) {
        _dateFilter.value = date
        getAllCompletedClasses(user.value?.email ?: return)
    }

    fun setHourFilter(hour: String) {
        _hourFilter.value = hour
        getAllCompletedClasses(user.value?.email ?: return)
    }
}