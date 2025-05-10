package com.example.scheduleit.models

data class Class(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val level: String? = null,
    val date: String? = null,
    val time: String? = null,
    val link: String? = null,
    val isFuture: Boolean? = null
) {
    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "id" to this.id,
            "title" to this.title,
            "description" to this.description,
            "level" to this.level,
            "date" to this.date,
            "time" to this.time,
            "link" to this.link,
            "isFuture" to this.isFuture
        )
    }
}

