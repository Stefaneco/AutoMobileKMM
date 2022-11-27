package com.example.automobile.profile.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name : String,
    val surname : String,
    val phone: String,
    val email: String
)
