package com.example.sharedandroid.doc.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.doc.DocDetailScreenState
import com.example.automobile.doc.model.Car
import com.example.automobile.doc.model.CarPart
import com.example.automobile.doc.model.RepairDocument
import com.example.automobile.profile.model.UserProfile
import com.example.sharedandroid.ui.FormBlock
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.R
import com.example.sharedandroid.doc.list.ScreenLoading

@Composable
fun DocumentDetailScreen(
    viewModel: DocumentDetailViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost,
    docId: String?,
    displayCustomer: Boolean,
    displayMechanic: Boolean
) {

    when(val state = viewModel.uiState.collectAsState().value) {
        is DocDetailScreenState.Success -> {
            RepairDocumentComposable(state.doc, displayMechanic, displayCustomer)
        }

        is DocDetailScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
        }
        else -> {
            ScreenLoading()
        }
    }
}

@Composable
fun RepairDocumentComposable(doc: RepairDocument, displayMechanic: Boolean, displayCustomer: Boolean){
    Column() {
        CarInfo(car = doc.car)
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        DatesInfo(startDate = doc.startDate, endDate = doc.endDate)
        if(displayCustomer){
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            PersonInfo(person = doc.customer)

        }
        if(displayMechanic){
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            PersonInfo(person = doc.mechanic)
        }
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            FormBlock(formTitle = stringResource(id = R.string.problem_description)) {
                Text(doc.problemDescription)
            }
            FormBlock(formTitle = stringResource(id = R.string.repair_description)) {
                Text(doc.repairDescription)
            }
            ReplacedPartsList(parts = doc.parts)
        }
    }
}

@Composable
fun CarInfo(car: Car){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.light_gray))) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Text(text = "${car.manufacturer} ${car.model} ${car.year}", fontSize = 30.sp)
            Text(text = car.vin)
            Text(text = car.registration)
        }
    }
}

@Composable
fun PersonInfo(person: UserProfile){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.light_gray))){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = person.name + " " + person.surname)
            Text(text = person.phone)
        }
    }
}

@Composable
fun DatesInfo(startDate: String, endDate: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(colorResource(id = R.color.light_gray))){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)){
            Text(text = "$startDate - $endDate")
        }
    }
}

@Composable
fun ReplacedPartsList(parts: List<CarPart>){
    FormBlock(formTitle = stringResource(id = R.string.replaced_parts)) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(parts) { part ->
                CarPartRow(carPart = part)
            }
        }
    }
}
