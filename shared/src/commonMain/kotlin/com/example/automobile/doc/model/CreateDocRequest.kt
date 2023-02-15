package com.example.automobile.doc.model

import com.example.automobile.profile.model.UserProfile

@kotlinx.serialization.Serializable
data class CreateDocRequest(
    val startDate: Long,
    val problemDescription: String,
    val customer: UserProfile,
    val car: Car
)