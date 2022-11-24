package com.example.clientandroidapp.di

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.interactors.*
import com.example.automobile.network.ISessionSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorsModule {
    @Provides
    @Singleton
    fun provideAuthInteractors(
        accountService: IAccountService,
        sessionSource: ISessionSource
    ) : AuthInteractors {
        return AuthInteractors(
            Login(accountService, sessionSource),
            IsValidEmail(),
            IsValidPassword(),
            GetSessionFromDevice(sessionSource)
        )
    }
}