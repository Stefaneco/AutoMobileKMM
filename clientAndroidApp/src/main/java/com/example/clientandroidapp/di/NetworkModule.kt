package com.example.clientandroidapp.di

import android.content.Context
import com.example.automobile.auth.AccountService
import com.example.automobile.auth.IAccountService
import com.example.automobile.network.*
import com.example.clientandroidapp.HttpRoutes
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