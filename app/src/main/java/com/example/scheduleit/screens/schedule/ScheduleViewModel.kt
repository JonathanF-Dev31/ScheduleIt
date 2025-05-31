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

    private val _classSelections: MutableStateFlow<Map<Int, Boolean>> = MutableStateFlow(emptyMap())
    val classSelections: StateFlow<Map<Int, Boolean>> = _classSelections

    private val _classes: MutableStateFlow<List<Class>> = MutableStateFlow(emptyList())
    val classes: StateFlow<List<Class>> = _classes

    fun toggleClassSelection(classId: Int, isSelected: Boolean) {
        val updatedSelections = classSelections.value.toMutableMap()
        updatedSelections[classId] = isSelected
        _classSelections.value = updatedSelections
    }

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
        _isLoading.value = true
        db.collection("class")
            .addSnapshotListener { classSnapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error escuchando cambios en las clases", e)
                    _isLoading.value = false
                    return@addSnapshotListener
                }

                val completedClasses = user.value?.completedClasses ?: emptyList()
                val allClasses = classSnapshot?.documents ?: emptyList()

                val notCompletedClasses = allClasses.filter { classDoc ->
                    val classTitle = classDoc.id
                    !completedClasses.contains(classTitle)
                }

                val remainingClasses = notCompletedClasses
                    .mapNotNull { doc -> doc.toObject(Class::class.java) }
                    .sortedBy { it.id }

                _completedClasses.value = remainingClasses
                _classes.value = remainingClasses

                _isLoading.value = false
            }
    }


    fun addClassToSchedule(userEmail: String, classItem: Class, date: String, hour: String) {
        val userDocRef = db.collection("users").document(userEmail)

        userDocRef.collection("scheduleClasses")
            .get()
            .addOnSuccessListener { querySnapshot ->

                val newClassDocumentName = classItem.title

                val classData = hashMapOf(
                    "id" to classItem.id,
                    "title" to classItem.title,
                    "description" to classItem.description,
                    "level" to classItem.level,
                    "time" to hour,
                    "date" to date,
                    "link" to classItem.link
                )

                userDocRef.collection("scheduleClasses")
                    .document(newClassDocumentName!!)
                    .set(classData)
                    .addOnSuccessListener {
                        Log.d("ScheduleViewModel", "Class added successfully with name $newClassDocumentName")

                        userDocRef.update("completedClasses", com.google.firebase.firestore.FieldValue.arrayUnion(classItem.title))
                            .addOnSuccessListener {
                                Log.d("ScheduleViewModel", "Class title added to completedClasses")
                            }
                            .addOnFailureListener { e ->
                                Log.e("ScheduleViewModel", "Error adding title to completedClasses", e)
                            }

                    }
                    .addOnFailureListener { e ->
                        Log.e("ScheduleViewModel", "Error adding class", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e("ScheduleViewModel", "Error fetching scheduleClasses", e)
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