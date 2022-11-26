package com.example.sharedandroid.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.auth.model.RegisterRequest
import com.example.sharedandroid.auth.AuthScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authInteractors: AuthInteractors
) : ViewModel()  {

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    fun register(name: String, surname: String, phone: String, email: String, password: String){
        if (!isValidRegistration(name, surname, phone, email, password)) return
        authInteractors.register(RegisterRequest(name, surname, phone, email, password)).onEach { dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()){
                _uiState.value = AuthScreenState.Error(dataState.message!!)
            }
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    fun isValidRegistration(name: String, surname: String, phone: String, email: String, password: String) : Boolean{
        return isValidName(name) && isValidEmail(email) &&
                isValidPassword(password) && isValidPhone(phone) && isValidSurname(surname)
    }

    fun isValidName(name: String) = authInteractors.isValidName(name)

    fun isValidSurname(surname: String) = authInteractors.isValidSurname(surname)

    fun isValidPhone(phone: String) = authInteractors.isValidPhone(phone)

    //Not using interactor because IDE displays error when using actual class with invoke
    //For release swap to interactor
    //fun isValidEmail(email: String) = authInteractors.isValidEmail(email)
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String) = authInteractors.isValidPassword(password)
}