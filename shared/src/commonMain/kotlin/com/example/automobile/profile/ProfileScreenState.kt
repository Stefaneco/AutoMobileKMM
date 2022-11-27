package com.example.automobile.profile

import com.example.automobile.profile.model.UserProfile

sealed class ProfileScreenState{
    object Loading : ProfileScreenState()
    class Success(val userProfile: UserProfile): ProfileScreenState()
    class Error(val message: String): ProfileScreenState()
}
