package com.example.sharedandroid.auth.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharedandroid.R
import com.example.sharedandroid.auth.AuthScreenState
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navController: NavController
) {
    var email by remember{ mutableStateOf("") }
    var loading = false
    val snackbarHostState = SnackbarHostState()
    val context = LocalContext.current

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            LaunchedEffect(snackbarHostState){
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.password_sent)
                )
            }
            if (!viewModel.isNavigatedOut){
                navController.navigate(NavigationRoutes.LOGIN) {
                    popUpTo(NavigationRoutes.FORGOT_PASSWORD){
                        inclusive = true
                    }
                }
                viewModel.isNavigatedOut = true
            }
        }
        is AuthScreenState.Error -> {
            LaunchedEffect(snackbarHostState){
                snackbarHostState.showSnackbar(
                    message = state.message
                )
            }
        }
        is AuthScreenState.Loading -> loading = true
        else -> {}
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        email = ValidatedTextField(
            hint = stringResource(id = R.string.email),
            isFieldValid = viewModel::isValidEmail,
            errorMessage = stringResource(id = R.string.incorrect_email),
            enabled = !loading
        )
        Button(
            modifier = Modifier.padding(5.dp),
            enabled = !loading && viewModel.isValidEmail(email),
            onClick = {
                viewModel.resetPassword(email)
            }
        ) {
            Text(stringResource(id = R.string.reset_password))
        }
    }
}