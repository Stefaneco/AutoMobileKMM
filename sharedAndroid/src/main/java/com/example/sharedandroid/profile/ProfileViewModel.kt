package com.example.sharedandroid.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.profile.ProfileScreenState
import com.example.automobile.profile.interactors.ProfileInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    profileInteractors: ProfileInteractors,
    private val authInteractors: AuthInteractors
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading)
    val uiState: StateFlow<ProfileScreenState> = _uiState

    init {
        profileInteractors.getUserProfile().onEach { dataState ->
            if(dataState.data != null) {
                _uiState.value = ProfileScreenState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()) {
                _uiState.value = ProfileScreenState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }

    fun logout(){
        authInteractors.logout()
    }
}