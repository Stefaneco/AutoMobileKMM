package com.example.automobile.auth

import com.example.automobile.auth.model.LoginRequest
import io.ktor.client.statement.*

interface IAccountService {
    suspend fun login(loginRequest: LoginRequest) : HttpResponse
}