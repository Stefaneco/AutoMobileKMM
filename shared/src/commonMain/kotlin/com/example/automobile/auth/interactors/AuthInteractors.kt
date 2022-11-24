package com.example.automobile.auth.interactors

data class AuthInteractors(
    val login: Login,
    val isValidEmail: IsValidEmail,
    val isValidPassword: IsValidPassword
)
