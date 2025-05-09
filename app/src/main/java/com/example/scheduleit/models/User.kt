package com.example.scheduleit.models

data class User(
    val id: Int? = null,
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null,
    val currentlyLevel: String? = null,
    val completedClasses: List<Class> = listOf()
) {
    fun toMap(): MutableMap<String, Any?>{
        return mutableMapOf(
            "id" to this.id,
            "name" to this.name,
            "lastName" to this.lastName,
            "email" to this.email,
            "password" to this.password,
            "currentlyLevel" to this.currentlyLevel,
            "completedClasses" to this.completedClasses
        )
    }
}
