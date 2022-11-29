package com.example.automobile.auth

import com.example.automobile.auth.model.ChangePasswordRequest
import com.example.automobile.auth.model.LoginRequest
import com.example.automobile.auth.model.RegisterRequest
import com.example.automobile.auth.model.ResetPasswordRequest
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

    override suspend fun register(registerRequest: RegisterRequest): HttpResponse {
        return client.post(httpRoutes.register()) {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }.body()
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): HttpResponse {
        return client.post(httpRoutes.resetPassword()){
            contentType(ContentType.Application.Json)
            setBody(resetPasswordRequest)
        }.body()
    }

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): HttpResponse {
        return client.post(httpRoutes.changePassword()){
            contentType(ContentType.Application.Json)
            setBody(changePasswordRequest)
        }.body()
    }
}