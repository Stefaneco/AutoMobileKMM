package com.example.sharedandroid.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.auth.AuthScreenState
import com.example.sharedandroid.R
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.ValidatedPasswordTextField
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
) {
    var newPassword by remember{ mutableStateOf("") }
    var oldPassword by remember{ mutableStateOf("") }
    var loading = false
    val context = LocalContext.current

    when(val state = viewModel.uiState.collectAsState().value){
        is AuthScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                snackbarHost.showSnackbar(context.getString(R.string.password_sent))
                navController.navigate(NavigationRoutes.PROFILE) {
                    popUpTo(NavigationRoutes.CHANGE_PASSWORD){
                        inclusive = true
                    }
                }
                viewModel.isNavigatedOut = true
            }
        }
        is AuthScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
        }
        is AuthScreenState.Loading -> loading = true
        else -> {}
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        newPassword = ValidatedPasswordTextField(
            hint = stringResource(id = R.string.new_password),
            isFieldValid = viewModel::isValidPassword,
            errorMessage = stringResource(id = R.string.incorrect_password),
            enabled = !loading
        )
        oldPassword = ValidatedPasswordTextField(
            hint = stringResource(id = R.string.old_password),
            isFieldValid = viewModel::isValidPassword,
            errorMessage = stringResource(id = R.string.incorrect_password),
            enabled = !loading
        )
        Button(
            modifier = Modifier.padding(5.dp),
            enabled = !loading && viewModel.areValidPasswords(newPassword, oldPassword),
            onClick = {
                viewModel.changePassword(newPassword, oldPassword)
            }
        ) {
            Text(stringResource(id = R.string.change_password))
        }
        if(loading) LoadingDotsAnimation(
            circleSize = 15.dp,
            modifier = Modifier.padding(20.dp)
        )
    }
}