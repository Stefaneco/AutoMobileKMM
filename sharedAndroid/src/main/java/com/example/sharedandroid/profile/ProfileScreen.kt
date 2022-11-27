package com.example.sharedandroid.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Password
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.profile.ProfileScreenState
import com.example.sharedandroid.R
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.ProfileOptionButton
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
) {
    var email by remember{ mutableStateOf("") }
    var name by remember{ mutableStateOf("") }
    var surname by remember{ mutableStateOf("") }
    var phone by remember{ mutableStateOf("") }
    var loading = true

    when(val state = viewModel.uiState.collectAsState().value){
        is ProfileScreenState.Success -> {
            name = state.userProfile.name
            email = state.userProfile.email
            phone = state.userProfile.phone
            surname = state.userProfile.surname
            loading = false
        }
        is ProfileScreenState.Error -> {
            loading = false
            snackbarHost.showSnackbar(state.message)
        }
        else -> {}
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        if(loading){
            LoadingDotsAnimation()
        }
        else{
            Text(text = "$name $surname", fontSize = 30.sp)
            Text(phone)
            Text(email)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if(!loading) {
            ProfileOptionButton(text = "Edit profile", icon = Icons.Rounded.Edit, onClick = {})
            ProfileOptionButton(text = "Change password", icon = Icons.Rounded.Password, onClick = {})
            ProfileOptionButton(text = "Logout", icon = Icons.Rounded.Logout,
                onClick = {
                viewModel.logout()
                navController.navigate(NavigationRoutes.LOGIN) {
                    popUpTo(NavigationRoutes.PROFILE){
                        inclusive = true
                    }
                }
            })
        }
    }

}