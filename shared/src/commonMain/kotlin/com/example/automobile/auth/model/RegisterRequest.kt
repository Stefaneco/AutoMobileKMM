package com.example.automobile.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest (
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val password: String
        )