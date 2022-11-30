package com.example.automobile.profile

import com.example.automobile.profile.model.UserProfile
import io.ktor.client.statement.*

interface IProfileService {
    suspend fun getUserProfile(): HttpResponse
    suspend fun updateUserProfile(userProfile: UserProfile): HttpResponse
}