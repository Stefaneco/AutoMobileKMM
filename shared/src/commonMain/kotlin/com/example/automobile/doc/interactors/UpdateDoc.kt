package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.doc.model.UpdateDocRequest
import com.example.automobile.network.model.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateDoc(
    private val docService: IDocService
)  {
    operator fun invoke(updateDocRequest: UpdateDocRequest): Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try{
            val updateDocResponse = docService.updateDoc(updateDocRequest)
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}