package com.example.automobile.profile

import io.ktor.client.statement.*

interface IProfileService {
    suspend fun getUserProfile(): HttpResponse
}