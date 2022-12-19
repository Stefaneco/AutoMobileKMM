package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.doc.model.Car
import com.example.automobile.network.model.RequestState
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCarWithVin(
    private val docService: IDocService
) {
    operator fun invoke(vin: String): Flow<RequestState<Car>> = flow {
        emit(RequestState.loading())
        try {
            val carResponse = docService.getCarWithVin(vin)
            emit(RequestState.data(data = carResponse.body()))
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}