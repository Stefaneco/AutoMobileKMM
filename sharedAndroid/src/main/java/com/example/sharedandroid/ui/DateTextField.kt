package com.example.sharedandroid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.sharedandroid.R

@Composable
fun DateTextField(
    hint: String,
    width: Float = 0.7f,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit = {},
    value: LocalDate = LocalDate.now(),
) : LocalDate {
    var pickedDate by remember {
        mutableStateOf(value)
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd.MM.yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    Column() {
        OutlinedTextField(
            modifier =
            Modifier
                .clickable(
                    enabled = false,
                    onClick = { dateDialogState.show() }
                )
                .fillMaxWidth(width)
                .padding(5.dp),
            value = formattedDate,
            onValueChange = onValueChanged,
            label = { Text(text = hint) },
            enabled = enabled,
            trailingIcon = {
                IconButton(
                    enabled = enabled,
                    onClick = { dateDialogState.show() }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        stringResource(id = R.string.pick_date),
                    )
                }
            })
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = stringResource(id = R.string.ok))
            negativeButton(text = stringResource(id = R.string.cancel))
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = R.string.pick_date),
            allowedDateValidator = {
                it >= LocalDate.now()
            }
        ) {
            pickedDate = it
        }
    }
    return pickedDate
}