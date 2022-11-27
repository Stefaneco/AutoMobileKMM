package com.example.automobile.auth.interactors

import com.example.automobile.auth.IAccountService
import com.example.automobile.network.ISessionSource

class Logout(
    private val accountService: IAccountService,
    private val sessionSource: ISessionSource
) {
    operator fun invoke(){
        sessionSource.removeSessionData()
    }
}