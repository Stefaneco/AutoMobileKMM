package com.example.automobile.auth.interactors

import com.example.automobile.network.ISessionSource
import com.example.automobile.network.model.SessionData

class GetSessionFromDevice(
    private val sessionSource: ISessionSource
) {
    operator fun invoke() : SessionData {
        return sessionSource.getSessionData()
    }
}