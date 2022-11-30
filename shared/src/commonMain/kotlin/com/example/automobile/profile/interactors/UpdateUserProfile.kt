package com.example.automobile.profile.interactors

import com.example.automobile.exceptions.BadRequestException
import com.example.automobile.exceptions.TooManyRequestsException
import com.example.automobile.network.model.RequestState
import com.example.automobile.profile.IProfileService
import com.example.automobile.profile.model.UserProfile
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateUserProfile(
    private val profileService: IProfileService
) {
    operator fun invoke(userProfile: UserProfile): Flow<RequestState<Unit>> = flow {
        emit(RequestState.loading())
        try {
            val updateUserProfileResponse = profileService.updateUserProfile(userProfile)
            if(updateUserProfileResponse.status == HttpStatusCode.BadRequest) throw BadRequestException()
            if(updateUserProfileResponse.status == HttpStatusCode.TooManyRequests) throw TooManyRequestsException()
            emit(RequestState.data())
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}