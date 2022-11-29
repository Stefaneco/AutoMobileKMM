package com.example.automobile.auth.interactors

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.model.ChangePasswordRequest
import com.example.automobile.exceptions.BadRequestException
import com.example.automobile.exceptions.InternalServerErrorException
import com.example.automobile.exceptions.TooManyRequestsException
import com.example.automobile.network.model.RequestState
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChangePassword(
    private val accountService: IAccountService
) {
    operator fun invoke(changePasswordRequest: ChangePasswordRequest): Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try{
            val changeResponse = accountService.changePassword(changePasswordRequest)
            if(changeResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(changeResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            if(changeResponse.status == HttpStatusCode.TooManyRequests) throw TooManyRequestsException()
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}