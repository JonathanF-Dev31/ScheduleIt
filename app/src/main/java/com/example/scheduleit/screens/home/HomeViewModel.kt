package com.example.scheduleit.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scheduleit.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.scheduleit.models.Class

class HomeViewModel: ViewModel() {
    private var db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user : StateFlow<User?> = _user

    private val _classes: MutableStateFlow<List<Class>> = MutableStateFlow(emptyList())
    val classes: StateFlow<List<Class>> = _classes

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
                    getAllScheduleClasses(email)
                }
            }
    }

    private fun getAllScheduleClasses(userEmail: String) {
        db.collection("users")
            .document(userEmail)
            .collection("scheduleClasses")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("HomeViewModel", "Error escuchando clases del usuario", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val fetchedClasses = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Class::class.java)
                    }
                    _classes.value = fetchedClasses
                    Log.d("HomeViewModel", "Clases actualizadas: ${fetchedClasses.size}")
                }
            }
    }


    fun removeClassFromUserSchedule(classTitle: String) {
        val userEmail = auth.currentUser?.email ?: return

        db.collection("users")
            .document(userEmail)
            .collection("scheduleClasses")
            .document(classTitle)
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Clase eliminada correctamente")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al eliminar clase", e)
            }
    }
}