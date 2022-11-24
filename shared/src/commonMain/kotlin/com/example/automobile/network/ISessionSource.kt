package com.example.automobile.network

import com.example.automobile.network.model.SessionData


interface ISessionSource {

    fun getSessionData() : SessionData
    fun updateSessionData(sessionData: SessionData)
}