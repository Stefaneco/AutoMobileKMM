package com.example.sharedandroid.auth.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.exceptions.NoSessionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
): ViewModel() {
    private val _uiState = MutableStateFlow<SplashScreenState>(SplashScreenState.Loading)
    val uiState: StateFlow<SplashScreenState> = _uiState
    var isNavigatedOut = false

    init {
        viewModelScope.launch {
            try {
                authInteractors.getSessionFromDevice()
                withContext(Dispatchers.IO) {
                    Thread.sleep(1000)
                    _uiState.value = SplashScreenState.Success
                }
            } catch (e: NoSessionException){
                withContext(Dispatchers.IO) {
                    Thread.sleep(1000)
                    _uiState.value = SplashScreenState.NoSession
                }
            } catch (e: Exception){
                Log.e("SplashViewModel", e.message ?: "Unknown error")
                _uiState.value = SplashScreenState.Error(e.message ?: "Unknown error")
            }
        }
    }
}