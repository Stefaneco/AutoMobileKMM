package com.example.sharedandroid.doc.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.automobile.doc.model.CarPart
import com.example.sharedandroid.R

@Composable
fun CarPartRow (
    carPart: CarPart
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_gray))
    ){
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        ){
            Text(text = carPart.name)
            Text(text = carPart.manufacturer + " " + carPart.catalogNumber)
            val isNewPartString = stringResource(id = R.string.is_new_part)
            Text(
                text =
                if(carPart.isNew) "$isNewPartString: " + stringResource(id = R.string.yes)
                else "$isNewPartString: " + stringResource(id = R.string.no)
            )
        }
    }
}