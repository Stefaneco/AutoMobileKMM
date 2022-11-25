package com.example.sharedandroid.auth.forgotPassword

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.sharedandroid.auth.AuthScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
): ViewModel() {
    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    fun resetPassword(email: String) {
        if (!isValidEmail(email)) return
        authInteractors.resetPassword(email).onEach {
                dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()){
                _uiState.value = AuthScreenState.Error(dataState.message!!)
                Log.e("LoginViewModel", dataState.message!!)
            }
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    //Not using interactor because IDE displays error when using actual class with invoke
    //For release swap to interactor
    //fun isValidEmail(email: String) = authInteractors.isValidEmail(email)
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}