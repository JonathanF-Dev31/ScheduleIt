package com.example.scheduleit.models

data class Class(
    val id: Int,
    val title: String,
    val date: String,
    val time: String,
    val link: String,
    val isFuture: Boolean
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "id" to this.id,
            "title" to this.title,
            "date" to this.date,
            "time" to this.time,
            "link" to this.link,
            "isFuture" to this.isFuture
        )
    }
}

