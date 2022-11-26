package com.example.automobile.auth.interactors

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.model.ResetPasswordRequest
import com.example.automobile.exceptions.BadRequestException
import com.example.automobile.exceptions.InternalServerErrorException
import com.example.automobile.exceptions.TooManyRequestsException
import com.example.automobile.network.model.RequestState
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResetPassword(
    private val accountService: IAccountService
) {
    operator fun invoke(email: String) : Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try{
            val resetResponse = accountService.resetPassword(ResetPasswordRequest(email))
            if(resetResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(resetResponse.status == HttpStatusCode.InternalServerError) throw InternalServerErrorException()
            if(resetResponse.status == HttpStatusCode.TooManyRequests) throw TooManyRequestsException()
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}