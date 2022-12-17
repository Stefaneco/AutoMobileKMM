package com.example.automobile.doc

import com.example.automobile.profile.model.UserProfile

sealed class CustomerState {
    object Loading : CustomerState()
    object Static : CustomerState()
    class Success(val userProfile: UserProfile): CustomerState()
    class Error(val message: String): CustomerState()
}