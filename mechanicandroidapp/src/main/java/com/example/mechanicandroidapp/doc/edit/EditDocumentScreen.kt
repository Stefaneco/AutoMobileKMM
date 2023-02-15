package com.example.mechanicandroidapp.doc.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.doc.DocDetailScreenState
import com.example.automobile.doc.model.CarPart
import com.example.sharedandroid.R
import com.example.sharedandroid.doc.detail.CarInfo
import com.example.sharedandroid.doc.detail.CarPartRow
import com.example.sharedandroid.doc.detail.DatesInfo
import com.example.sharedandroid.doc.list.ScreenLoading
import com.example.sharedandroid.ui.FormBlock
import com.example.sharedandroid.ui.SnackbarHost
import com.example.sharedandroid.ui.ValidatedTextField
import com.example.sharedandroid.util.NavigationRoutes

@Composable
fun EditDocumentScreen(
    viewModel: EditDocumentViewModel = hiltViewModel(),
    snackbarHost: SnackbarHost,
    navController: NavController,
    docId: String?
) {

    when(val state = viewModel.uiState.collectAsState().value) {
        is DocDetailScreenState.Success -> {
            Column {
                CarInfo(car = state.doc.car)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                DatesInfo(startDate = state.doc.startDate)
                Descriptions()
                ReplacedParts()
            }
            ControlButtons(navController = navController)
        }

        is DocDetailScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
        }

        is DocDetailScreenState.NoChanges -> {
            if(!viewModel.isNavigatedOut) {
                navController.navigate(NavigationRoutes.DOCUMENT_LIST){
                    popUpTo(NavigationRoutes.EDIT_DOCUMENT)
                }
                viewModel.isNavigatedOut = true
                snackbarHost.showSnackbar(stringResource(id = R.string.no_changes_made))
            }

        }
        is DocDetailScreenState.Saved -> {
            if(!viewModel.isNavigatedOut) {
                viewModel.isNavigatedOut = true
                snackbarHost.showSnackbar(stringResource(id = R.string.saved_successfully))
                navController.navigate(NavigationRoutes.DOCUMENT_LIST) {
                    popUpTo(NavigationRoutes.EDIT_DOCUMENT)
                }
            }
        }
        else -> {
            ScreenLoading()
        }
    }
}

@Composable
fun ControlButtons(navController : NavController, viewModel: EditDocumentViewModel = hiltViewModel()){
    Column( modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        FloatingActionButton(
            backgroundColor = Color.Red,
            onClick = {
                viewModel.saveChanges(true)
            //navController.navigate(NavigationRoutes.EDIT_DOCUMENT_ARGS)
        }) {
            Icon(Icons.Filled.Lock, contentDescription = "")
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
        FloatingActionButton(
            onClick = {
                viewModel.saveChanges()
            //navController.navigate(NavigationRoutes.EDIT_DOCUMENT_ARGS)
        }) {
            Icon(Icons.Filled.Done, contentDescription = "")
        }
    }
}

@Composable
fun Descriptions(viewModel: EditDocumentViewModel = hiltViewModel()){

    var problemDescription by remember { viewModel.problemDescription }
    var repairDescription by remember { viewModel.repairDescription }

    Column(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        FormBlock(formTitle = stringResource(id = R.string.problem_description)) {
            ValidatedTextField(
                hint = stringResource(id = R.string.problem_description),
                isFieldValid = viewModel::isValidProblemDescription,
                errorMessage = stringResource(id = R.string.incorrect_problem_description),
                value = problemDescription,
                onValueChanged = { problemDescription = it },
                isSingleLine = false,
                width = 0.9f
            )
        }
        FormBlock(formTitle = stringResource(id = R.string.repair_description)) {
            ValidatedTextField(
                hint = stringResource(id = R.string.repair_description),
                isFieldValid = viewModel::isValidRepairDescription,
                errorMessage = stringResource(id = R.string.incorrect_repair_description),
                value = repairDescription,
                onValueChanged = { repairDescription = it },
                isSingleLine = false,
                width = 0.9f
            )
        }
    }
}

@Composable
fun ReplacedParts(viewModel: EditDocumentViewModel = hiltViewModel()){

    val parts by remember { viewModel.parts }

    FormBlock(formTitle = stringResource(id = R.string.replaced_parts)) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(parts) { part ->
                RemovableCarPartRow(carPart = part) {
                    viewModel.removePart(part)
                }
            }
            item {
                AddPart()
            }
        }
    }
}

@Composable
fun AddPart(viewModel: EditDocumentViewModel = hiltViewModel()) {

    var displayForm by remember { mutableStateOf(false) }
    var manufacturer by remember { mutableStateOf("") }
    var catalogNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_gray))
            .clickable {
                displayForm = true
            }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(!displayForm)
            Icon(Icons.Filled.Add,
                contentDescription = "",
                Modifier.size(40.dp))
            else{
                ValidatedTextField(
                    hint = stringResource(id = R.string.part_name),
                    isFieldValid = viewModel::isValidPartName,
                    errorMessage = stringResource(id = R.string.incorrect_part_name),
                    value = name,
                    onValueChanged = { name = it }
                )
                ValidatedTextField(
                    hint = stringResource(id = R.string.part_manufacturer),
                    isFieldValid = viewModel::isValidPartManufacturer,
                    errorMessage = stringResource(id = R.string.incorrect_part_manufacturer),
                    value = manufacturer,
                    onValueChanged = { manufacturer = it }
                )
                ValidatedTextField(
                    hint = stringResource(id = R.string.catalog_number),
                    isFieldValid = viewModel::isValidPartCatalogNumber,
                    errorMessage = stringResource(id = R.string.incorrect_catalog_number),
                    value = catalogNumber,
                    onValueChanged = { catalogNumber = it }
                )
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            displayForm = false
                            viewModel.addPart(name,manufacturer,catalogNumber,true)
                            name = ""
                            manufacturer = ""
                            catalogNumber = "" },
                        enabled = viewModel.isValidPart(name, manufacturer, catalogNumber)
                    ) {
                        Text(text = stringResource(id = R.string.state_new))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        modifier = Modifier.width(150.dp),
                        onClick = {
                            displayForm = false
                            viewModel.addPart(name,manufacturer,catalogNumber,false)
                            name = ""
                            manufacturer = ""
                            catalogNumber = "" },
                        enabled = viewModel.isValidPart(name, manufacturer, catalogNumber)
                    ) {
                        Text(text = stringResource(id = R.string.state_used))
                    }
                }
            }
        }
    }
}

@Composable
fun RemovableCarPartRow(carPart: CarPart, remove : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.light_gray)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.8f)){
            CarPartRow(carPart = carPart)
        }
        IconButton(
            onClick = { remove() },
            Modifier
                //.background(colorResource(id = R.color.light_gray))
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                "",
            )
        }
    }
}


