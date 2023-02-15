package com.example.mechanicandroidapp.doc.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sharedandroid.doc.list.DocumentListScreen
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun DocumentListScreenMechanic(
    navController: NavController,
    snackbarHost: SnackbarHost
) {
    DocumentListScreen(navController = navController, snackbarHost = snackbarHost)
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        FloatingActionButton(onClick = {
            navController.navigate(NavigationRoutes.CREATE_DOCUMENT)
        }) {
            Icon(Icons.Filled.Add, contentDescription = "")
        }
    }
}