package com.example.automobile.network

interface IHttpRoutes {
    //AUTH
    fun refresh(): String
    fun login(): String
    fun register(): String
    fun resetPassword(): String
    fun changePassword(): String

    //PROFILE
    fun getUserProfile(): String
    fun updateProfile(): String

    //DOC
    fun getCarWithVin(): String
    fun getCustomerWithPhone(): String
    fun createDoc(): String
    fun getDocs(): String
    fun getDoc(id: Int): String
}