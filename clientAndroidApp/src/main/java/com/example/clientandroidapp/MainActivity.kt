package com.example.clientandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clientandroidapp.ui.theme.AutoMobileTheme
import com.example.sharedandroid.auth.RegisterScreen
import com.example.sharedandroid.auth.forgotPassword.ForgotPasswordScreen
import com.example.sharedandroid.auth.login.LoginScreen
import com.example.sharedandroid.auth.splash.SplashScreen
import com.example.sharedandroid.profile.ProfileScreen
import com.example.sharedandroid.util.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoMobileTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavigationRoutes.SPLASH){
                    composable(NavigationRoutes.LOGIN){ LoginScreen(navController = navController) }
                    composable(NavigationRoutes.SPLASH){ SplashScreen(navController = navController) }
                    composable(NavigationRoutes.PROFILE){ ProfileScreen(navController = navController) }
                    composable(NavigationRoutes.REGISTER){ RegisterScreen(navController = navController) }
                    composable(NavigationRoutes.FORGOT_PASSWORD){ ForgotPasswordScreen(navController = navController) }
                }
            }
        }
    }
}