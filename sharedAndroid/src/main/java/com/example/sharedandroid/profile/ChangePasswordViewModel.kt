package com.example.sharedandroid.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.AuthScreenState
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.auth.model.ChangePasswordRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel(){

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    fun changePassword(newPassword: String, oldPassword: String) {
        if(!areValidPasswords(newPassword, oldPassword)) return
        authInteractors.changePassword(ChangePasswordRequest(newPassword, oldPassword)).onEach { dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()){
                _uiState.value = AuthScreenState.Error(dataState.message!!)
            }
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    fun areValidPasswords(newPassword: String, oldPassword: String) : Boolean{
        return (isValidPassword(newPassword) && isValidPassword(oldPassword))
    }

    fun isValidPassword(password: String) = authInteractors.isValidPassword(password)
}