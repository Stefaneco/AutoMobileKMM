package com.example.automobile.auth

sealed class AuthScreenState {
    object Static : AuthScreenState()
    object Loading : AuthScreenState()
    object Success : AuthScreenState()
    class Error(val message: String): AuthScreenState()
}