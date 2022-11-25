package com.example.automobile.auth

import com.example.automobile.auth.model.LoginRequest
import com.example.automobile.auth.model.ResetPasswordRequest
import io.ktor.client.statement.*

interface IAccountService {
    suspend fun login(loginRequest: LoginRequest) : HttpResponse
    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): HttpResponse
}