package com.example.automobile.auth.interactors

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.model.RegisterRequest
import com.example.automobile.exceptions.BadRequestException
import com.example.automobile.exceptions.InternalServerErrorException
import com.example.automobile.network.ISessionSource
import com.example.automobile.network.model.RequestState
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Register(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(registerRequest: RegisterRequest) : Flow<RequestState<Unit>> = flow {

        emit(RequestState.loading())
        try{
            val registerResponse = accountService.register(registerRequest)
            if(registerResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(registerResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            sessionSource.updateSessionData(registerResponse.body())
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}