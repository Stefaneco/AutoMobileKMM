package com.example.automobile.doc.model

@kotlinx.serialization.Serializable
data class UpdateDocRequest(
    val id: Int,
    val problemDescription: String,
    val repairDescription: String,
    val parts : List<CarPart>,
    val closedTimestamp: Long = 0
)
