package com.example.sharedandroid.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ValidatedTextField(
    hint: String,
    isFieldValid: (fieldValue: String) -> Boolean,
    errorMessage: String,
    width: Float = 0.8f,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit = {},
    value: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isSingleLine: Boolean = true
) : String {
    var wasFieldClicked by remember { mutableStateOf(false) }
    var wasFiledClickedOut by remember { mutableStateOf(false) }
    var displayError by remember { mutableStateOf(false) }

    Column() {
        OutlinedTextField(
            modifier =
            Modifier
                .fillMaxWidth(width)
                .padding(5.dp)
                .onFocusEvent {
                    if (it.hasFocus) {
                        wasFieldClicked = true
                    }
                    if (!it.hasFocus && wasFieldClicked) {
                        wasFiledClickedOut = true
                        displayError = !isFieldValid(value)
                    }

                },
            value = value,
            label = { Text(text = hint) },
            onValueChange = {
                onValueChanged(it)
                displayError = !isFieldValid(it) && wasFiledClickedOut
            },
            singleLine = isSingleLine,
            isError = displayError,
            enabled = enabled,
            keyboardOptions = keyboardOptions
        )
        if(displayError)
            Text(color = Color.Red,
                text = errorMessage)
    }
    return value
}