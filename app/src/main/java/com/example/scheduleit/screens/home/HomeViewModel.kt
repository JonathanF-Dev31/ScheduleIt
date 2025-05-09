package com.example.scheduleit.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleit.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel: ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user : StateFlow<User?> = _user

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            val result = getCurrentUser()
            _user.value = result
        }
    }

    suspend fun getCurrentUser(): User? {
        return try {
            val userEmail = auth.currentUser?.email
            if (userEmail != null) {
                val snapshot = db.collection("users").document(userEmail).get().await()
                val user = snapshot.toObject(User::class.java)
                user
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
