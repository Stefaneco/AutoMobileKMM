package com.example.sharedandroid.doc.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.automobile.doc.DocListScreenState
import com.example.automobile.doc.model.DocListItem
import com.example.sharedandroid.ui.LoadingDotsAnimation
import com.example.sharedandroid.ui.SnackbarHost

@Composable
fun DocumentListScreen(
    viewModel: DocumentListViewModel = hiltViewModel(),
    navController: NavController,
    snackbarHost: SnackbarHost
) {

    when(val state = viewModel.uiState.collectAsState().value) {
        is DocListScreenState.Loading -> {
            ScreenLoading()
        }
        is DocListScreenState.Success -> {
            DocList(docs = state.docListItems, navController)
        }
        is DocListScreenState.Error -> {
            snackbarHost.showSnackbar(state.message)
        }
        else -> {}
    }
}

@Composable
fun DocList(docs: List<DocListItem>, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LazyColumn(
            contentPadding = PaddingValues(vertical = 28.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(docs) { doc ->
                RepairRow(
                    docId = doc.id,
                    carName = "${doc.manufacturer} ${doc.model} ${doc.year}",
                    date = "${doc.startDate} - ${doc.endDate}",
                    personName = "${doc.name} ${doc.surname}",
                    navController = navController)
            }
        }
    }
}

@Composable
fun ScreenLoading(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        LoadingDotsAnimation()
    }
}