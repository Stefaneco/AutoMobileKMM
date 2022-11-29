package com.example.automobile.auth.interactors

data class AuthInteractors(
    val login: Login,
    val isValidEmail: IsValidEmail,
    val isValidPassword: IsValidPassword,
    val getSessionFromDevice: GetSessionFromDevice,
    val resetPassword: ResetPassword,
    val isValidName: IsValidName,
    val isValidSurname: IsValidSurname,
    val isValidPhone: IsValidPhone,
    val register: Register,
    val logout: Logout,
    val changePassword: ChangePassword
)
