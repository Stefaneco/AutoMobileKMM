package com.example.automobile.network

import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*


object MockApiEngine {

    private val responseHeaders =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    private var errorStatusCode = HttpStatusCode.BadRequest

    fun get() = engine

    private var isSuccess = true

    fun givenFailure(statusCode: HttpStatusCode = HttpStatusCode.BadRequest) {
        isSuccess = false
        errorStatusCode = statusCode
    }

    fun givenSuccess() {
        isSuccess = true
    }

    private val engine = MockEngine { request ->
        handleSearchRequest(request)
    }

    private fun MockRequestHandleScope.handleSearchRequest(request: HttpRequestData): HttpResponseData {
        if (!isSuccess) return errorResponse()
        when (request.method) {
            HttpMethod.Post -> {
                return when (request.url.encodedPath) {
                    "/api/account/login" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/account/refresh" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/account/register" -> respond(mockTokenResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/account/recover" -> respond(mockEmptyResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/account/change_password" ->  respond(mockEmptyResponse, HttpStatusCode.OK, responseHeaders)
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
            HttpMethod.Get -> {
                return when (request.url.encodedPath) {
                    "/api/user" -> respond(mockProfileResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/car" -> respond(mockCarResponse, HttpStatusCode.OK, responseHeaders)
                    "/api/customer" -> respond(mockProfileResponse, HttpStatusCode.OK, responseHeaders)
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
            HttpMethod.Put -> {
                return when (request.url.encodedPath) {
                    "/api/user" -> respond(mockEmptyResponse, HttpStatusCode.OK, responseHeaders)
                    else -> {
                        error("Unhandled ${request.url.encodedPath}")
                    }
                }
            }
            else -> {
                error("Unhandled ${request.method.value}")
            }
        }
    }

    private fun MockRequestHandleScope.errorResponse(): HttpResponseData {
        return respond(
            content = "",
            status = errorStatusCode,
            headers = responseHeaders
        )
    }

    private val mockTokenResponse = """{
        |"jwt":"jwt",
        |"refreshToken":"refreshToken"
        |}""".trimMargin()

    private val mockEmptyResponse = """
    """.trimIndent()

    private val mockProfileResponse = """{
        "name":"Andrzej",
        "surname":"Testowy",
        "phone":"48808101202",
        "email":"andrzej@testowy.com"  
    }""".trimMargin()

    private val mockCarResponse = """
        "vin":"VIN1234567890VIN1",
        "registration": "RJA2137P",
        "manufacturer": "Toyota",
        "model": "Rav4",
        "year": 2018
    """.trimIndent()
}