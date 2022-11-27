package com.example.sharedandroid.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.auth.AuthScreenState
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.ValidatedPasswordTextField
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.util.NavigationRoutes
import com.example.automobile.R
import com.example.sharedandroid.ui.SnackbarHost


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
)
{
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    var loading = false
    val snackbarHostState = SnackbarHostState()

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                navController.navigate(NavigationRoutes.PROFILE) {
                    popUpTo(NavigationRoutes.LOGIN){
                        inclusive = true
                    }
                }
                viewModel.isNavigatedOut = true
            }
        }
        is AuthScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
/*            LaunchedEffect(snackbarHostState){
                snackbarHostState.showSnackbar(
                    message = state.message
                )
            }*/
        }
        is AuthScreenState.Loading -> loading = true
        else -> {}
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        email = ValidatedTextField(
            hint = stringResource(id = R.string.email),
            isFieldValid = viewModel::isValidEmail,
            errorMessage = stringResource(id = R.string.incorrect_email),
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
            enabled = !loading && viewModel.isValidPassword(password) && viewModel.isValidEmail(email),
            onClick = {
                viewModel.logIn(email, password)
            }
        ) {
            Text(stringResource(id = R.string.login))
        }
        if(loading) LoadingDotsAnimation(
            circleSize = 15.dp,
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = stringResource(id = R.string.forgot_password),
            Modifier
                .padding(5.dp)
                .clickable { if (!loading) navController.navigate(NavigationRoutes.FORGOT_PASSWORD) }
        )
        Text(
            text = stringResource(id = R.string.register),
            Modifier
                .padding(5.dp)
                .clickable { if (!loading) navController.navigate(NavigationRoutes.REGISTER) }
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SnackbarHost(
            hostState = snackbarHostState)
    }
}