package com.example.automobile.doc.model

@kotlinx.serialization.Serializable
data class DocListItem(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val name: String,
    val surname: String,
    val manufacturer: String,
    val model: String,
    val year: Int
)