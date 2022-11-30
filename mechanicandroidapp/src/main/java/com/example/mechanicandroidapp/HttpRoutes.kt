package com.example.mechanicandroidapp

import com.example.automobile.network.IHttpRoutes

class HttpRoutes : IHttpRoutes {
    val BASE_URL = "https://10.0.2.2:7254/api"
    val LOGIN = "$BASE_URL/account/login"
    val REFRESH = "$BASE_URL/account/refresh"
    val REGISTER = "$BASE_URL/account/register"
    val RESET_PASSWORD = "$BASE_URL/account/recover"
    val USER_PROFILE = "$BASE_URL/user"
    val CHANGE_PASSWORD = "$BASE_URL/account/change_password"

    override fun login(): String = LOGIN

    override fun refresh(): String = REFRESH

    override fun register(): String = REGISTER

    override fun resetPassword(): String = RESET_PASSWORD

    override fun getUserProfile(): String = USER_PROFILE

    override fun changePassword(): String = CHANGE_PASSWORD

    override fun updateProfile(): String = USER_PROFILE
}