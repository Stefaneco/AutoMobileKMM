package com.example.automobile.profile

import com.example.automobile.network.IHttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ProfileService(
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
) : IProfileService {
    override suspend fun getUserProfile(): HttpResponse {
        return client.get(httpRoutes.getUserProfile()).body()
    }
}