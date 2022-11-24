package com.example.mechanicandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mechanicandroidapp.ui.theme.AutoMobileTheme
import com.example.sharedandroid.auth.login.LoginScreen
import com.example.sharedandroid.util.NavigationRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoMobileTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NavigationRoutes.LOGIN){
                    composable(NavigationRoutes.LOGIN){
                        LoginScreen(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}