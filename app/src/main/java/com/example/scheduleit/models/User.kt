package com.example.scheduleit.models

data class User(
    val id: Int? = null,
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val photo: String? = null,
    val password: String? = null,
    val currentlyLevel: String? = null,
    val completedClasses: ArrayList<String>? = null,
    val scheduleClasses: List<Class> = listOf()
) {
    fun toMap(): MutableMap<String, Any?>{
        return mutableMapOf(
            "id" to this.id,
            "name" to this.name,
            "lastName" to this.lastName,
            "email" to this.email,
            "phone" to this.phone,
            "photo" to this.photo,
            "password" to this.password,
            "currentlyLevel" to this.currentlyLevel,
            "completedClasses" to this.completedClasses,
            "scheduleClasses" to this.scheduleClasses
        )
    }
}
