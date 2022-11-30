package com.example.automobile.profile.interactors

import com.example.automobile.network.model.RequestState
import com.example.automobile.profile.IProfileService
import com.example.automobile.profile.model.UserProfile
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserProfile(
    private val profileService: IProfileService
) {
    operator fun invoke(): Flow<RequestState<UserProfile>> = flow {
        emit(RequestState.loading())
        try {
            val getUserProfileResponse = profileService.getUserProfile()
            emit(RequestState.data(data = getUserProfileResponse.body()))
        } catch (e: Exception){
            emit(RequestState.error(e.message ?: "Unknown Error"))
        }
    }
}