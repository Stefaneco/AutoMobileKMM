package com.example.automobile.doc

import com.example.automobile.doc.model.CreateDocRequest
import com.example.automobile.doc.model.UpdateDocRequest
import io.ktor.client.statement.*

interface IDocService {

    suspend fun getCarWithVin(vin: String) : HttpResponse
    suspend fun getClientWithPhone(phone: String) : HttpResponse
    suspend fun createDoc(createDocRequest: CreateDocRequest) : HttpResponse
    suspend fun getDocs(): HttpResponse
    suspend fun getDoc(id: Int): HttpResponse
    suspend fun updateDoc(updateDocRequest: UpdateDocRequest): HttpResponse
}