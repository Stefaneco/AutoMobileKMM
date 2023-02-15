package com.example.automobile.doc.model

import kotlinx.serialization.Serializable

@Serializable
data class Car (
    val vin: String,
    val registration: String,
    val manufacturer: String,
    val model: String,
    val year: Int
        )