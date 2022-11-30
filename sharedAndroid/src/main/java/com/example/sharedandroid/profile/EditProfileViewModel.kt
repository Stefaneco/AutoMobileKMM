package com.example.sharedandroid.profile

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.AuthScreenState
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.profile.interactors.ProfileInteractors
import com.example.automobile.profile.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val profileInteractors: ProfileInteractors,
    private val authInteractors: AuthInteractors
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    fun updateProfileData(name: String, surname: String, phone: String, email: String) {
        if (!isValidUpdate(name, surname, phone, email)) return
        profileInteractors.updateUserProfile(UserProfile(name, surname, phone, email)).onEach { dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()) _uiState.value = AuthScreenState.Error(dataState.message!!)
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    fun isValidUpdate(name: String, surname: String, phone: String, email: String) : Boolean{
        return isValidName(name) && isValidEmail(email) &&
                isValidSurname(surname) && isValidPhone(phone)
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
}