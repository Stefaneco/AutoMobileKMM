package com.example.clientandroidapp

import com.example.automobile.network.IHttpRoutes

class HttpRoutes : IHttpRoutes {
    val BASE_URL = "https://10.0.2.2:7254/api"
    val LOGIN = "$BASE_URL/account/login"
    val REFRESH = "$BASE_URL/account/refresh"
    val REGISTER = "$BASE_URL/account/register"
    val RESET_PASSWORD = "$BASE_URL/account/recover"
    val USER_PROFILE = "$BASE_URL/user"
    val CHANGE_PASSWORD = "$BASE_URL/account/change_password"
    val CREATE_DOC = "$BASE_URL/repair"
    val GET_DOCS = "$BASE_URL/repair"

    override fun login(): String = LOGIN

    override fun refresh(): String = REFRESH

    override fun register(): String = REGISTER

    override fun resetPassword(): String = RESET_PASSWORD

    override fun getUserProfile(): String = USER_PROFILE

    override fun changePassword(): String = CHANGE_PASSWORD

    override fun updateProfile(): String {
        TODO("Not yet implemented")
    }

    override fun getCarWithVin(): String {
        TODO("Not yet implemented")
    }

    override fun getCustomerWithPhone(): String {
        TODO("Not yet implemented")
    }

    override fun createDoc(): String {
        TODO("Not yet implemented")
    }

    override fun getDocs(): String = GET_DOCS
}