package com.example.automobile.doc.interactors

import com.example.automobile.doc.IDocService
import com.example.automobile.doc.model.DocListItem
import com.example.automobile.network.model.RequestState
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDocs(
    private val docService: IDocService
) {
    operator fun invoke(): Flow<RequestState<List<DocListItem>>> = flow {
        emit(RequestState.loading())
        try {
            val docsResponse = docService.getDocs()
            emit(RequestState.data(data = docsResponse.body()))
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}