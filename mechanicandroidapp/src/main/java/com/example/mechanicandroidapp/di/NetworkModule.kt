package com.example.mechanicandroidapp.di

import android.content.Context
import com.example.automobile.auth.AccountService
import com.example.automobile.auth.IAccountService
import com.example.automobile.doc.DocService
import com.example.automobile.doc.IDocService
import com.example.automobile.network.*
import com.example.automobile.profile.IProfileService
import com.example.automobile.profile.ProfileService
import com.example.mechanicandroidapp.HttpRoutes
import com.example.sharedandroid.network.SessionSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideAccountService(httpClient : HttpClient, httpRoutes: IHttpRoutes): IAccountService {
        return AccountService(httpClient, httpRoutes)
    }

    @Singleton
    @Provides
    fun provideProfileService(httpClient: HttpClient, httpRoutes: IHttpRoutes): IProfileService {
        return ProfileService(httpClient, httpRoutes)
    }

    @Singleton
    @Provides
    fun provideDocService(httpClient: HttpClient, httpRoutes: IHttpRoutes): IDocService {
        return DocService(httpClient, httpRoutes)
    }

    @Singleton
    @Provides
    fun provideHttpClient(sessionSource: ISessionSource, httpRoutes: IHttpRoutes): HttpClient {
        //MockApiEngine.givenFailure(HttpStatusCode.BadRequest)
        return KtorClientFactory(sessionSource, httpRoutes, MockApiEngine.get()).build()
        //return KtorClientFactory(sessionSource, httpRoutes, CIO.create()).build()
    }

    @Singleton
    @Provides
    fun provideSessionSource(@ApplicationContext context: Context): ISessionSource {
        return SessionSource(context)
    }

    @Singleton
    @Provides
    fun provideHttpRoutes() : IHttpRoutes {
        return HttpRoutes()
    }
}