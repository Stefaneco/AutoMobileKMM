package com.example.automobile.doc

import io.ktor.client.statement.*

interface IDocService {

    suspend fun getCarWithVin(vin: String) : HttpResponse
    suspend fun getClientWithPhone(phone: String) : HttpResponse
}