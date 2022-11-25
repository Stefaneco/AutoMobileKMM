package com.example.automobile.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest (
    @SerialName("email")
    val email: String
    )