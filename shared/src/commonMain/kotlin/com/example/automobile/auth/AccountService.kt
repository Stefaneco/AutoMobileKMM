package com.example.automobile.auth

import com.example.automobile.auth.model.LoginRequest
import com.example.automobile.network.IHttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class AccountService(
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
): IAccountService {
    override suspend fun login(loginRequest: LoginRequest): HttpResponse {
        return client.post(httpRoutes.login()) {
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body()
    }
}