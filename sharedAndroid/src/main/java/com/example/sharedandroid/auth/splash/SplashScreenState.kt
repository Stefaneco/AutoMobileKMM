package com.example.sharedandroid.auth.splash

sealed class SplashScreenState {
    object Loading: SplashScreenState()
    object NoSession: SplashScreenState()
    object Success: SplashScreenState()
    class Error(val message: String): SplashScreenState()
}