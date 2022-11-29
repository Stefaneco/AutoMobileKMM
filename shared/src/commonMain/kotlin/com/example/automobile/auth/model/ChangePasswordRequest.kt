package com.example.automobile.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequest(
    val newPassword: String,
    val oldPassword: String
)