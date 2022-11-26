package com.example.automobile.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionData (
    val jwt: String,
    val refreshToken: String
)