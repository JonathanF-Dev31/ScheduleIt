package com.example.scheduleit.components

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

fun getCurrentDateTime(): Pair<String, String> {
    val date = SimpleDateFormat("dd/MM/yyyy").format(Date())
    val time = SimpleDateFormat("hh:mm:ss a").format(Date())
    return Pair(date, time)
}

fun fetchUserName(onResult: (String) -> Unit) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(userId).get()
        .addOnSuccessListener { document ->
            val name = document.getString("name") ?: "Usuario"
            onResult(name)
        }
        .addOnFailureListener {
            onResult("Usuario")
        }
}
