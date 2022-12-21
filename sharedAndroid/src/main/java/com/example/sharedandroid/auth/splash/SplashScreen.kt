package com.example.sharedandroid.auth.splash

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.util.NavigationRoutes


@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val snackbarHostState = SnackbarHostState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LoadingDotsAnimation(circleSize = 15.dp)
    }

    when(val state = viewModel.uiState.collectAsState().value){
        is SplashScreenState.Success -> {
            if (!viewModel.isNavigatedOut){
                navController.navigate(NavigationRoutes.MAIN_GRAPH) {
                    popUpTo(NavigationRoutes.SPLASH){
                        inclusive = true
                    }
                }
                viewModel.isNavigatedOut = true
            }
        }
        is SplashScreenState.NoSession -> {
            if (!viewModel.isNavigatedOut) {
                navController.navigate(NavigationRoutes.LOGIN) {
                    popUpTo(NavigationRoutes.SPLASH) {
                        inclusive = true
                    }
                }
                viewModel.isNavigatedOut = true
            }
        }
        is SplashScreenState.Error -> {
            LaunchedEffect(snackbarHostState){
                snackbarHostState.showSnackbar(
                    message = state.message
                )
            }
            (context as? Activity)?.finish()
        }
        else -> {}
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