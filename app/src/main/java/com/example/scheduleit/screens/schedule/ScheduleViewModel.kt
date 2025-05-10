package com.example.scheduleit.screens.schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scheduleit.models.Class
import com.example.scheduleit.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScheduleViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user : StateFlow<User?> = _user

    private val _completedClasses: MutableStateFlow<List<Class>> = MutableStateFlow(emptyList())
    val completedClasses: StateFlow<List<Class>> = _completedClasses

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

    private fun getAllCompletedClasses(userEmail: String) {

    }
}