package com.example.scheduleit.models

data class Level(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val classes: List<Class> = listOf()
){
    fun toMap(): MutableMap<String, Any?>{
        return mutableMapOf(
            "id" to this.id,
            "name" to this.name,
            "description" to this.description,
            "classes" to this.classes
        )
    }
}
