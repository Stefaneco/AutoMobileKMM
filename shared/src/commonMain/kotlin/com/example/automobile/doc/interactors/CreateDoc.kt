package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.doc.model.CreateDocRequest
import com.example.automobile.network.model.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateDoc(
    private val docService: IDocService
) {
    operator fun invoke(createDocRequest: CreateDocRequest): Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try{
            val createDocResponse = docService.createDoc(createDocRequest)
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}