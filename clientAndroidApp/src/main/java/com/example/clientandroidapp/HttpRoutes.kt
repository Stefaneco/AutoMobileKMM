package com.example.clientandroidapp

import com.example.automobile.network.IHttpRoutes

class HttpRoutes : IHttpRoutes {
    val BASE_URL = "https://10.0.2.2:7254/api"
    val LOGIN = "$BASE_URL/account/login"
    val REFRESH = "$BASE_URL/account/refresh"
    val REGISTER = "$BASE_URL/account/register"
    val RESET_PASSWORD = "$BASE_URL/account/recover"

    override fun login(): String = LOGIN

    override fun refresh(): String = REFRESH

    override fun register(): String = REGISTER

    override fun resetPassword(): String = RESET_PASSWORD
    override fun getUserProfile(): String {
        TODO("Not yet implemented")
    }

    override fun changePassword(): String {
        TODO("Not yet implemented")
    }

    override fun updateProfile(): String {
        TODO("Not yet implemented")
    }

    override fun getCarWithVin(): String {
        TODO("Not yet implemented")
    }

    override fun getCustomerWithPhone(): String {
        TODO("Not yet implemented")
    }
}