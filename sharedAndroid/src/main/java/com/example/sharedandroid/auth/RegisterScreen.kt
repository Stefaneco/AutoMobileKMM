package com.example.sharedandroid.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.automobile.R
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun RegisterScreen(navController: NavController) {
    Text(text = "REGISTER SCREEN")
    Text(
        text = stringResource(id = R.string.register),
        Modifier
            .padding(5.dp)
            .clickable { navController.navigate(NavigationRoutes.LOGIN) }
    )
}