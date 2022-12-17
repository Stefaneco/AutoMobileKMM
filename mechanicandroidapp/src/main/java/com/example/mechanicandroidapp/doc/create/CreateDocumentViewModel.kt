package com.example.mechanicandroidapp.doc.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.AuthScreenState
import com.example.automobile.doc.CarState
import com.example.automobile.doc.CustomerState
import com.example.automobile.doc.interactors.DocInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateDocumentViewModel @Inject constructor(
    private val docInteractors: DocInteractors
): ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    private val _carState = MutableStateFlow<CarState>(CarState.Static)
    val carState: StateFlow<CarState> = _carState

    private val _customerState = MutableStateFlow<CustomerState>(CustomerState.Static)
    val customerState: StateFlow<CustomerState> = _customerState

    fun createDocument(){

    }

    fun getClient(phone: String){
        if(!isValidPhoneNumber(phone)) return
        docInteractors.getCustomerWithPhone(phone).onEach { dataState ->
            if(dataState.isLoading) {
                _customerState.value = CustomerState.Loading
            }
            else if(dataState.data != null){
                _customerState.value = CustomerState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()){
                _customerState.value = CustomerState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }

    fun getCarWithVin(vin: String){
        if(!isValidVin(vin)) return
        docInteractors.getCarWithVin(vin).onEach { dataState ->
            if(dataState.isLoading) {
                _carState.value = CarState.Loading
            }
            else if(dataState.data != null){
                _carState.value = CarState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()){
                _carState.value = CarState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }

    fun isValidCar(){

    }

    fun isValidProblemDescription(description: String): Boolean {
        return true
    }

    fun isValidPhoneNumber(phone: String): Boolean {
        return true
    }

    fun isValidName(name: String) : Boolean {
        return true
    }

    fun isValidSurname(surname: String) : Boolean {
        return true
    }

    fun isValidEmail(email: String) : Boolean {
        return true
    }

    fun isValidVin(vin: String) : Boolean {
        return true
    }

    fun isValidRegistration(registration: String) : Boolean {
        return true
    }

    fun isValidManufacturer(manufacturer: String) : Boolean {
        return true
    }

    fun isValidModel(model: String) : Boolean {
        return true
    }

    fun isValidYear(year: String) : Boolean {
        return true
    }

}