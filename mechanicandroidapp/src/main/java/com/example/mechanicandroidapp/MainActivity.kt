package com.example.mechanicandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mechanicandroidapp.doc.create.CreateDocumentScreen
import com.example.mechanicandroidapp.ui.theme.AutoMobileTheme
import com.example.sharedandroid.auth.forgotPassword.ForgotPasswordScreen
import com.example.sharedandroid.auth.login.LoginScreen
import com.example.sharedandroid.auth.register.RegisterScreen
import com.example.sharedandroid.auth.splash.SplashScreen
import com.example.sharedandroid.profile.ChangePasswordScreen
import com.example.sharedandroid.profile.EditProfileScreen
import com.example.sharedandroid.profile.ProfileScreen
import com.example.sharedandroid.ui.rememberSnackbarHostState
import com.example.sharedandroid.util.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoMobileTheme {
                val snackbarHost = rememberSnackbarHostState()
                val navController = rememberNavController()
                Scaffold(scaffoldState = snackbarHost.scaffoldState) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = NavigationRoutes.SPLASH
                    )
                    {
                        composable(NavigationRoutes.LOGIN){ LoginScreen(navController = navController, snackbarHost = snackbarHost) }
                        composable(NavigationRoutes.SPLASH){ SplashScreen(navController = navController) }
                        composable(NavigationRoutes.PROFILE){ ProfileScreen(navController = navController, snackbarHost = snackbarHost) }
                        composable(NavigationRoutes.REGISTER){ RegisterScreen(navController = navController, snackbarHost = snackbarHost) }
                        composable(NavigationRoutes.FORGOT_PASSWORD){ ForgotPasswordScreen(navController = navController, snackbarHost = snackbarHost) }
                        composable(NavigationRoutes.CHANGE_PASSWORD){ ChangePasswordScreen(navController = navController, snackbarHost = snackbarHost) }
                        composable(NavigationRoutes.EDIT_PROFILE) {
                            backStackEntry ->
                            EditProfileScreen(
                                navController = navController,
                                snackbarHost = snackbarHost,
                                currentName = backStackEntry.arguments?.getString("name"),
                                currentSurname = backStackEntry.arguments?.getString("surname"),
                                currentEmail = backStackEntry.arguments?.getString("email"),
                                currentPhone = backStackEntry.arguments?.getString("phone")
                            )
                        }
                        composable(NavigationRoutes.CREATE_DOCUMENT) { CreateDocumentScreen(
                            navController = navController,
                            snackbarHost = snackbarHost
                        )}
                    }
                }
            }
        }
    }
}