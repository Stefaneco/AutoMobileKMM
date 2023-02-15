package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.network.model.RequestState
import com.example.automobile.profile.model.UserProfile
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCustomerWithPhone(
    private val docService: IDocService
) {
    operator fun invoke(phone: String): Flow<RequestState<UserProfile>> = flow {
        emit(RequestState.loading())
        try {
            val clientResponse = docService.getClientWithPhone(phone)
            emit(RequestState.data(data = clientResponse.body()))
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}