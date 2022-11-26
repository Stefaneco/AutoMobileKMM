package com.example.sharedandroid.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SnackbarHost(
    private val coroutineScope: CoroutineScope,
    val scaffoldState: ScaffoldState
) {
    fun showSnackbar(message: String, duration: SnackbarDuration = SnackbarDuration.Long) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }
}

@Composable
fun rememberSnackbarHostState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = remember {
            SnackbarHostState()
        }
    )
) = remember(coroutineScope, scaffoldState) {
    SnackbarHost(coroutineScope, scaffoldState)
}