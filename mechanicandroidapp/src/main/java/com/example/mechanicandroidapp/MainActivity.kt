package com.example.mechanicandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mechanicandroidapp.doc.create.CreateDocumentScreen
import com.example.mechanicandroidapp.doc.detail.DocumentDetailScreenMechanic
import com.example.mechanicandroidapp.doc.edit.EditDocumentScreen
import com.example.mechanicandroidapp.doc.list.DocumentListScreenMechanic
import com.example.mechanicandroidapp.ui.theme.AutoMobileTheme
import com.example.sharedandroid.auth.forgotPassword.ForgotPasswordScreen
import com.example.sharedandroid.auth.login.LoginScreen
import com.example.sharedandroid.auth.register.RegisterScreen
import com.example.sharedandroid.auth.splash.SplashScreen
import com.example.sharedandroid.profile.ChangePasswordScreen
import com.example.sharedandroid.profile.EditProfileScreen
import com.example.sharedandroid.profile.ProfileScreen
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.rememberSnackbarHostState
import com.example.sharedandroid.util.BottomNavItem
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
                        startDestination = NavigationRoutes.SPLASH){

                        composable(NavigationRoutes.PROFILE){
                            BottomNavBarWrapper(navController = navController, snackbarHost = snackbarHost) {
                                ProfileScreen(snackbarHost = snackbarHost, navController = navController)
                            }
                        }
                        composable(NavigationRoutes.DOCUMENT_LIST) {
                            BottomNavBarWrapper(navController = navController, snackbarHost = snackbarHost) {
                                DocumentListScreenMechanic(
                                    navController = navController,
                                    snackbarHost = snackbarHost
                                )
                            }
                        }

                        composable(NavigationRoutes.FORGOT_PASSWORD){
                            ForgotPasswordScreen(
                                navController = navController,
                                snackbarHost = snackbarHost
                            )
                        }
                        composable(NavigationRoutes.LOGIN){
                            LoginScreen(
                                navController = navController,
                                snackbarHost = snackbarHost
                            )
                        }
                        composable(NavigationRoutes.SPLASH){
                            SplashScreen(
                                navController = navController
                            )
                        }
                        composable(NavigationRoutes.REGISTER){
                            RegisterScreen(
                                navController = navController,
                                snackbarHost = snackbarHost
                            )
                        }
                        composable(NavigationRoutes.DOCUMENT_DETAILS) { backStackEntry ->
                            DocumentDetailScreenMechanic(
                                navController = navController,
                                snackbarHost = snackbarHost,
                                docId = backStackEntry.arguments?.getString("docId")
                            )
                        }
                        composable(NavigationRoutes.EDIT_DOCUMENT) { backStackEntry ->
                            EditDocumentScreen(
                                snackbarHost = snackbarHost,
                                navController =navController,
                                docId = backStackEntry.arguments?.getString("docId"))

                        }
                        composable(NavigationRoutes.CHANGE_PASSWORD){
                            ChangePasswordScreen(
                                navController = navController,
                                snackbarHost = snackbarHost
                            )
                        }
                        composable(NavigationRoutes.EDIT_PROFILE) { backStackEntry ->
                            EditProfileScreen(
                                navController = navController,
                                snackbarHost = snackbarHost,
                                currentName = backStackEntry.arguments?.getString("name"),
                                currentSurname = backStackEntry.arguments?.getString("surname"),
                                currentEmail = backStackEntry.arguments?.getString("email"),
                                currentPhone = backStackEntry.arguments?.getString("phone")
                            )
                        }
                        composable(NavigationRoutes.CREATE_DOCUMENT) {
                            CreateDocumentScreen(
                                navController = navController,
                                snackbarHost = snackbarHost
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavItem>,
        navController: NavController,
        onItemClick: (BottomNavItem) -> Unit
    ){
        val backStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            backgroundColor = Color.LightGray,
            elevation = 5.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    icon = {
                        Icon(item.icon, item.name)
                    }
                )
            }
        }
    }

    @Composable
    fun BottomNavBarWrapper(
        navController: NavController,
        snackbarHost: SnackbarHost,
        screen: @Composable () -> Unit
    ){
        Scaffold(
            //scaffoldState = snackbarHost.scaffoldState,
            bottomBar = {
                BottomNavigationBar(
                    items = listOf(
                        BottomNavItem(
                            name = "Repairs",
                            route = NavigationRoutes.DOCUMENT_LIST,
                            icon = Icons.Default.List
                        ),

                        BottomNavItem(
                            name = "Profile",
                            route = NavigationRoutes.PROFILE,
                            icon = Icons.Default.Person
                        )
                    ),
                    navController = navController,
                    onItemClick = {
                        navController.navigate(it.route) {
                            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                                inclusive =  true
                            }
                        }
                    })
            }
        ){ padding ->
            Box(modifier = Modifier.padding(padding)){
                screen()
            }
        }
    }

    @Composable
    fun MainNavGraph(
        mainNavController: NavHostController,
        snackbarHost: SnackbarHost,
        navController: NavController
    ) {
        Scaffold(
            scaffoldState = snackbarHost.scaffoldState,
            bottomBar = {
                BottomNavigationBar(
                    items = listOf(
                        BottomNavItem(
                            name = "Repairs",
                            route = NavigationRoutes.DOCUMENT_LIST,
                            icon = Icons.Default.List
                        ),

                        BottomNavItem(
                            name = "Profile",
                            route = NavigationRoutes.PROFILE,
                            icon = Icons.Default.Person
                        )
                    ),
                    navController = mainNavController,
                    onItemClick = {
                        mainNavController.navigate(it.route) {
                            popUpTo(mainNavController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                                inclusive =  true
                            }
                        }
                    })
            }
        ) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = mainNavController,
                route = NavigationRoutes.MAIN_GRAPH,
                startDestination = NavigationRoutes.DOCUMENT_LIST){
                composable(NavigationRoutes.PROFILE){ ProfileScreen(
                    snackbarHost = snackbarHost,
                    navController = navController) }
                composable(NavigationRoutes.DOCUMENT_LIST) { DocumentListScreenMechanic(
                    navController = navController,
                    snackbarHost = snackbarHost
                )}
            }
        }
    }
}