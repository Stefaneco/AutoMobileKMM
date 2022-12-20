package com.example.automobile.doc

import com.example.automobile.doc.model.CreateDocRequest
import com.example.automobile.network.IHttpRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class DocService(
    private val client: HttpClient,
    private val httpRoutes: IHttpRoutes
) : IDocService {
    override suspend fun getCarWithVin(vin: String): HttpResponse {
        return client.get(httpRoutes.getCarWithVin()){
            contentType(ContentType.Application.Json)
            setBody(vin)
        }.body()
    }

    override suspend fun getClientWithPhone(phone: String): HttpResponse {
        return client.get(httpRoutes.getCustomerWithPhone()){
            contentType(ContentType.Application.Json)
            setBody(phone)
        }.body()
    }

    override suspend fun createDoc(createDocRequest: CreateDocRequest): HttpResponse {
        return client.post(httpRoutes.createDoc()){
            contentType(ContentType.Application.Json)
            setBody(createDocRequest)
        }.body()
    }

    override suspend fun getDocs(): HttpResponse {
        return client.get(httpRoutes.getDocs()).body()
    }
}