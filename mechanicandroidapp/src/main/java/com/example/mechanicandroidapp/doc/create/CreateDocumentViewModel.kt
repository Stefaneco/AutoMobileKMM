package com.example.mechanicandroidapp.doc.create

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.auth.AuthScreenState
import com.example.automobile.auth.interactors.AuthInteractors
import com.example.automobile.doc.CarState
import com.example.automobile.doc.CustomerState
import com.example.automobile.doc.interactors.DocInteractors
import com.example.automobile.doc.model.Car
import com.example.automobile.doc.model.CreateDocRequest
import com.example.automobile.profile.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.sql.Timestamp
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateDocumentViewModel @Inject constructor(
    private val docInteractors: DocInteractors,
    private val authInteractors: AuthInteractors
): ViewModel() {

    private val _uiState = MutableStateFlow<AuthScreenState>(AuthScreenState.Static)
    val uiState: StateFlow<AuthScreenState> = _uiState
    var isNavigatedOut = false

    private val _carState = MutableStateFlow<CarState>(CarState.Static)
    val carState: StateFlow<CarState> = _carState

    private val _customerState = MutableStateFlow<CustomerState>(CustomerState.Static)
    val customerState: StateFlow<CustomerState> = _customerState

    fun createDocument(car: Car, customer: UserProfile, problemDescription: String, startDate: LocalDate){
        if(!isValidDocument(car, customer, problemDescription)) return
        val startDateTimestamp = Timestamp.valueOf(startDate.atStartOfDay().toString())
        docInteractors.createDoc(CreateDocRequest(startDateTimestamp.time,problemDescription,customer, car)).onEach { dataState ->
            if(dataState.isLoading) _uiState.value = AuthScreenState.Loading
            else if (!dataState.message.isNullOrEmpty()){
                _uiState.value = AuthScreenState.Error(dataState.message!!)
            }
            else _uiState.value = AuthScreenState.Success
        }.launchIn(viewModelScope)
    }

    fun getCustomer(phone: String){
        Log.e("CreateDocumentViewModel", phone)
        if(!isValidPhoneNumber(phone)) return
        docInteractors.getCustomerWithPhone(phone).onEach { dataState ->
            if(dataState.isLoading) {
                Log.e("CreateDocumentViewModel", "Loading")
                _customerState.value = CustomerState.Loading
            }
            else if(dataState.data != null){
                Log.e("CreateDocumentViewModel", "Data")
                _customerState.value = CustomerState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()){
                Log.e("CreateDocumentViewModel", dataState.message!!)
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

    fun isValidDocument(car: Car, customer: UserProfile, problemDescription: String): Boolean {
        return isValidCar(car) && isValidCustomer(customer) && isValidProblemDescription(problemDescription)
    }

    private fun isValidCar(car: Car) : Boolean{
        return isValidVin(car.vin) && isValidRegistration(car.registration) && isValidYear(car.year.toString())
                && isValidManufacturer(car.manufacturer) && isValidModel(car.model)
    }

    private fun isValidCustomer(customer: UserProfile) : Boolean{
        return isValidName(customer.name) && isValidEmail(customer.email)
                && isValidPhoneNumber(customer.phone) && isValidSurname(customer.surname)
    }

    fun isValidProblemDescription(description: String): Boolean = docInteractors.isValidProblemDescription(description)

    fun isValidPhoneNumber(phone: String): Boolean = authInteractors.isValidPhone(phone)

    fun isValidName(name: String) : Boolean = authInteractors.isValidName(name)

    fun isValidSurname(surname: String) : Boolean = authInteractors.isValidSurname(surname)

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidVin(vin: String) : Boolean = docInteractors.isValidVin(vin)

    fun isValidRegistration(registration: String) : Boolean = docInteractors.isValidRegistration(registration)

    fun isValidManufacturer(manufacturer: String) : Boolean = docInteractors.isValidManufacturer(manufacturer)

    fun isValidModel(model: String) : Boolean = docInteractors.isValidModel(model)

    fun isValidYear(year: String) : Boolean {
        return (year.toIntOrNull() ?: 0) <= LocalDate.now().year + 1
    }
}