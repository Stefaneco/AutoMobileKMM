package com.example.automobile.network

interface IHttpRoutes {
    fun refresh(): String
    fun login(): String
    fun register(): String
    fun resetPassword(): String
    fun getUserProfile(): String
}