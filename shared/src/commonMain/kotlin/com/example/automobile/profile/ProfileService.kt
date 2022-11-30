package com.example.automobile.profile

import com.example.automobile.network.IHttpRoutes
import com.example.automobile.profile.model.UserProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ProfileService(
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
) : IProfileService {
    override suspend fun getUserProfile(): HttpResponse {
        return client.get(httpRoutes.getUserProfile()).body()
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): HttpResponse {
        return client.put(httpRoutes.updateProfile()){
            contentType(ContentType.Application.Json)
            setBody(userProfile)
        }.body()
    }
}