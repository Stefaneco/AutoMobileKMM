package com.example.automobile.doc.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class CarPart(
    val name: String,
    val manufacturer: String,
    @SerialName("catalog_number")
    val catalogNumber: String,
    val isNew: Boolean
)