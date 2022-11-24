package com.example.automobile.auth.interactors

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.model.LoginRequest
import com.example.automobile.network.ISessionSource
import com.example.automobile.network.model.RequestState
import com.example.automobile.exceptions.BadRequestException
import com.example.automobile.exceptions.InternalServerErrorException
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Login(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(loginRequest: LoginRequest) : Flow<RequestState<Unit>> = flow {

        emit(RequestState.loading())
        try{
            val loginResponse = accountService.login(loginRequest)
            if(loginResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(loginResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            sessionSource.updateSessionData(loginResponse.body())
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}