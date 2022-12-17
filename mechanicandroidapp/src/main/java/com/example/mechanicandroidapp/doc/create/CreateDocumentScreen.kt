package com.example.mechanicandroidapp.doc.create

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.auth.AuthScreenState
import com.example.automobile.doc.CarState
import com.example.sharedandroid.ui.DateTextField
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.R
import com.example.sharedandroid.ui.FormBlock
import com.example.sharedandroid.util.NavigationRoutes
import java.time.LocalDate

@Composable
fun CreateDocumentScreen(
    viewModel: CreateDocumentViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
) {
    var startDate by remember{ mutableStateOf(LocalDate.now()) }
    var problemDescription by remember{ mutableStateOf("") }

    //Car
    var vin by rememberSaveable { mutableStateOf("") }
    var registration by rememberSaveable { mutableStateOf("") }
    var manufacturer by rememberSaveable { mutableStateOf("") }
    var model by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }

    //Client
    var email by rememberSaveable { mutableStateOf("") }
    var surname by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }

    var loading = false
    var carLoading = false
    var clientLoading = false

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                snackbarHost.showSnackbar(stringResource(id = R.string.document_created_successfully))
                viewModel.isNavigatedOut = true
                navController.navigate(NavigationRoutes.DOCUMENT_LIST){
                    popUpTo(NavigationRoutes.CREATE_DOCUMENT){
                        inclusive = true
                    }
                }
            }
        }
        is AuthScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
        }
        is AuthScreenState.Loading -> loading = true
        else -> {}
    }

    when(val carState = viewModel.carState.collectAsState().value){
        is CarState.Success -> {
            with(carState){
                vin = car.vin
                registration = car.registration
                manufacturer = car.manufacturer
                model = car.model
                year = car.year.toString()
            }
        }
        is CarState.Loading -> {
            carLoading = true
        }
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //Start date
        startDate = DateTextField(hint = stringResource(id = R.string.start_date))
        //Problem desc
        ValidatedTextField(
            value = problemDescription,
            hint = stringResource(id = R.string.problem_description),
            isFieldValid = viewModel::isValidProblemDescription,
            errorMessage = stringResource(id = R.string.incorrect_problem_description),
            isSingleLine = false)
        //Client
        FormBlock(formTitle = stringResource(id = R.string.client)) {
            ValidatedTextField(
                value = phone,
                hint = stringResource(id = R.string.phone),
                isFieldValid = viewModel::isValidPhoneNumber,
                errorMessage = stringResource(id = R.string.incorrect_phone),
            onValueChanged = { _phone ->
                phone = _phone
                viewModel.getClient(_phone)
            })

            ValidatedTextField(
                value = name,
                hint = stringResource(id = R.string.name),
                isFieldValid = viewModel::isValidName,
                errorMessage = stringResource(id = R.string.incorrect_name),
                onValueChanged = {name = it})

            ValidatedTextField(
                value = surname,
                hint = stringResource(id = R.string.surname),
                isFieldValid = viewModel::isValidSurname,
                errorMessage = stringResource(id = R.string.incorrect_surname),
                onValueChanged = {surname = it})

            ValidatedTextField(
                value = email,
                hint = stringResource(id = R.string.email),
                isFieldValid = viewModel::isValidEmail,
                errorMessage = stringResource(id = R.string.incorrect_email),
                onValueChanged = {email = it})
        }
        //Car
        FormBlock(formTitle = stringResource(id = R.string.car)) {
            ValidatedTextField(
                value = vin,
                hint = stringResource(id = R.string.vin),
                isFieldValid = viewModel::isValidVin,
                errorMessage = stringResource(id = R.string.incorrect_vin),
                onValueChanged = { _vin ->
                viewModel.getCarWithVin(_vin)
            })

            ValidatedTextField(
                value = registration,
                hint = stringResource(id = R.string.registration_number),
                isFieldValid = viewModel::isValidRegistration,
                errorMessage = stringResource(id = R.string.incorrect_registration_number),
                onValueChanged = {registration = it})

            ValidatedTextField(
                value = manufacturer,
                hint = stringResource(id = R.string.car_manufacturer),
                isFieldValid = viewModel::isValidManufacturer,
                errorMessage = stringResource(id = R.string.incorrect_car_manufacturer),
                onValueChanged = {manufacturer = it})

            ValidatedTextField(
                value = model,
                hint = stringResource(id = R.string.car_model),
                isFieldValid = viewModel::isValidModel,
                errorMessage = stringResource(id = R.string.incorrect_car_model),
                onValueChanged = {model = it})

            ValidatedTextField(
                value = year,
                hint = stringResource(id = R.string.car_year),
                isFieldValid = viewModel::isValidYear,
                errorMessage = stringResource(id = R.string.incorrect_car_year),
                onValueChanged = {year = it})
        }

        Button(
            modifier = Modifier.padding(5.dp),
            //enabled = !loading && viewModel.isValidPassword(password) && viewModel.isValidEmail(email),
            onClick = {
                viewModel.createDocument()
            }
        ) {
            Text(stringResource(id = R.string.create_document))
        }
    }

}