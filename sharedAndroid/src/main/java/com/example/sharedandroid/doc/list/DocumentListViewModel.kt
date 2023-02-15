package com.example.sharedandroid.doc.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.doc.DocListScreenState
import com.example.automobile.doc.interactors.DocInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
open class DocumentListViewModel @Inject constructor(
    private val docInteractors: DocInteractors
): ViewModel() {

    private val _uiState = MutableStateFlow<DocListScreenState>(DocListScreenState.Loading)
    val uiState: StateFlow<DocListScreenState> = _uiState

    init {
        docInteractors.getDocs().onEach { dataState ->
            if(dataState.data != null) {
                _uiState.value = DocListScreenState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()) {
                _uiState.value = DocListScreenState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }
}