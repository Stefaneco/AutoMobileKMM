package com.example.automobile.doc

import com.example.automobile.profile.model.UserProfile

sealed class ClientState {
    object Loading : ClientState()
    object Static : ClientState()
    class Success(val userProfile: UserProfile): ClientState()
    class Error(val message: String): ClientState()
}