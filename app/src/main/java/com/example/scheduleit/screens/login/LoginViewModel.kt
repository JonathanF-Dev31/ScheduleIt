package com.example.scheduleit.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit)
            = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Firebase", "signInWithEmailAndPassword:success")
                        home()
                    } else {
                        Log.w("Firebase", "signInWithEmailAndPassword:failure", task.exception)
                    }
                }
        }
        catch (e: Exception) {
            Log.w("Firebase", "signInWithEmailAndPassword: ${e.message}")

        }
    }
}