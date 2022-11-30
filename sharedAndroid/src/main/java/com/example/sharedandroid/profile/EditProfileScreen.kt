package com.example.sharedandroid.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.auth.AuthScreenState
import com.example.sharedandroid.R
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost,
    currentName: String?,
    currentSurname: String?,
    currentPhone: String?,
    currentEmail: String?
) {
    var name by remember{ mutableStateOf(currentName ?: "") }
    var surname by remember{ mutableStateOf(currentSurname?: "") }
    var phone by remember{ mutableStateOf(currentPhone?: "") }
    var email by remember{ mutableStateOf(currentEmail?: "") }
    var loading = false

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                snackbarHost.showSnackbar(stringResource(id = R.string.profile_updated_successfully))
                viewModel.isNavigatedOut = true
                navController.navigate(NavigationRoutes.PROFILE){
                    popUpTo(NavigationRoutes.EDIT_PROFILE){
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        name = ValidatedTextField(
            value = name,
            hint = stringResource(id = R.string.name),
            isFieldValid = viewModel::isValidName,
            errorMessage = stringResource(id = R.string.incorrect_name)
        )
        surname = ValidatedTextField(
            value = surname,
            hint = stringResource(id = R.string.surname),
            isFieldValid = viewModel::isValidSurname,
            errorMessage = stringResource(id = R.string.incorrect_surname)
        )
        phone = ValidatedTextField(
            value = phone,
            hint = stringResource(id = R.string.phone),
            isFieldValid = viewModel::isValidPhone,
            errorMessage = stringResource(id = R.string.incorrect_phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        email = ValidatedTextField(
            value = email,
            hint = stringResource(id = R.string.email),
            isFieldValid = viewModel::isValidEmail,
            errorMessage = stringResource(id = R.string.incorrect_email)
        )
        Button(
            modifier = Modifier.padding(5.dp),
            enabled = !loading && viewModel.isValidUpdate(name, surname, phone, email),
            onClick = {viewModel.updateProfileData(name, surname, phone, email)}
        ) {
            Text(stringResource(id = R.string.register))
        }
        if(loading) LoadingDotsAnimation(
            circleSize = 15.dp,
            modifier = Modifier.padding(20.dp)
        )
    }
}