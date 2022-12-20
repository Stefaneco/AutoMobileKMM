package com.example.sharedandroid.doc.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sharedandroid.R
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun RepairRow(
    docId: Int,
    carName: String,
    date: String,
    personName: String,
    navController: NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_gray))
            .clickable {
                navController.navigate(NavigationRoutes.DOCUMENT_DETAILS_ARGS.format(docId.toString()))
            }
    ){
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(text = carName,
                modifier = Modifier,
            fontSize = 20.sp)
            Text(text = date)
            Text(text = personName)
        }
    }
}