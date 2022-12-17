package com.example.sharedandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sharedandroid.R

@Composable
fun FormBlock(
    formTitle: String,
    content: @Composable () -> Unit
) {

    var isDroppedDown by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val image = if (isDroppedDown)
                Icons.Filled.ArrowDropUp
            else Icons.Filled.ArrowDropDown

            val description = if (isDroppedDown) stringResource(id = R.string.drop_up)
            else stringResource(id = R.string.drop_down)

            Text(text = formTitle)

            IconButton(
                onClick = { isDroppedDown = !isDroppedDown }) {
                Icon(
                    imageVector = image,
                    description,
                )
            }
        }
        if(isDroppedDown) content()
    }
}