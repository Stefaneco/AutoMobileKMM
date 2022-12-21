package com.example.sharedandroid.auth.register

import androidx.compose.foundation.clickable
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
import com.example.sharedandroid.R
import com.example.automobile.auth.AuthScreenState
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.ValidatedPasswordTextField
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
) {
    var email by remember{ mutableStateOf("") }
    var surname by remember{ mutableStateOf("") }
    var phone by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var name by remember{ mutableStateOf("") }
    var loading = false

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                viewModel.isNavigatedOut = true
                navController.navigate(NavigationRoutes.MAIN_GRAPH){
                    popUpTo(NavigationRoutes.REGISTER){
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
        ValidatedTextField(
            value = name,
            hint = stringResource(id = R.string.name),
            isFieldValid = viewModel::isValidName,
            errorMessage = stringResource(id = R.string.incorrect_name),
            onValueChanged = {name = it},
            enabled = !loading
        )
        ValidatedTextField(
            value = surname,
            hint = stringResource(id = R.string.surname),
            isFieldValid = viewModel::isValidSurname,
            errorMessage = stringResource(id = R.string.incorrect_surname),
            onValueChanged = {surname = it},
            enabled = !loading
        )
        ValidatedTextField(
            value = phone,
            hint = stringResource(id = R.string.phone),
            isFieldValid = viewModel::isValidPhone,
            errorMessage = stringResource(id = R.string.incorrect_phone),
            onValueChanged = {phone = it},
            enabled = !loading,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        ValidatedTextField(
            value = email,
            hint = stringResource(id = R.string.email),
            isFieldValid = viewModel::isValidEmail,
            errorMessage = stringResource(id = R.string.incorrect_email),
            onValueChanged = {email = it},
            enabled = !loading
        )
        password = ValidatedPasswordTextField(
            hint = stringResource(id = R.string.password),
            isFieldValid = viewModel::isValidPassword,
            errorMessage = stringResource(id = R.string.incorrect_password),
            enabled = !loading
        )
        Button(
            modifier = Modifier.padding(5.dp),
            enabled = !loading && viewModel.isValidRegistration(name, surname, phone, email, password),
            onClick = {viewModel.register(name, surname, phone, email, password)}
        ) {
            Text(stringResource(id = R.string.register))
        }
        if(loading) LoadingDotsAnimation(
            circleSize = 15.dp,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = stringResource(id = R.string.login),
            Modifier
                .padding(5.dp)
                .clickable { if (!loading) navController.navigate(NavigationRoutes.LOGIN) }
        )
    }
}