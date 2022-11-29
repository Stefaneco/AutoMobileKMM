package com.example.mechanicandroidapp.di

import com.example.automobile.auth.IAccountService
import com.example.automobile.auth.interactors.*
import com.example.automobile.network.ISessionSource
import com.example.automobile.profile.IProfileService
import com.example.automobile.profile.interactors.GetUserProfile
import com.example.automobile.profile.interactors.ProfileInteractors
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
            GetSessionFromDevice(sessionSource),
            ResetPassword(accountService),
            IsValidName(),
            IsValidSurname(),
            IsValidPhone(),
            Register(accountService, sessionSource),
            Logout(accountService, sessionSource),
            ChangePassword(accountService)
        )
    }

    @Provides
    @Singleton
    fun provideProfileInteractors(
        profileService: IProfileService
    ) : ProfileInteractors {
        return ProfileInteractors(
            GetUserProfile(profileService)
        )
    }
}