package com.example.scheduleit.screens.profile

import android.util.Log
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
import android.content.Context
import android.net.Uri
import java.io.File



class ProfileViewModel : ViewModel() {
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

    fun updateUserPhoto(email: String, uri: String) {
        db.collection("users").document(email)
            .update("photo", uri)
            .addOnSuccessListener {
                Log.d("Profile", "Photo updated")
                getUser()
            }
            .addOnFailureListener {
                Log.e("Profile", "Error updating photo", it)
            }
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val uniqueFileName = "profile_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, uniqueFileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return Uri.fromFile(file)
    }

}