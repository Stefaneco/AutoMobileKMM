package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.doc.model.RepairDocument
import com.example.automobile.network.model.RequestState
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDoc(
    private val docService: IDocService
) {
    operator fun invoke(id: Int): Flow<RequestState<RepairDocument>> = flow {
        emit(RequestState.loading())
        try {
            val docResponse = docService.getDoc(id)
            emit(RequestState.data(data = docResponse.body()))
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}