package com.example.sharedandroid.doc.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.doc.DocDetailScreenState
import com.example.automobile.doc.interactors.DocInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DocumentDetailViewModel @Inject constructor(
    docInteractors: DocInteractors,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow<DocDetailScreenState>(DocDetailScreenState.Loading)
    val uiState: StateFlow<DocDetailScreenState> = _uiState

    init {
        val docId = checkNotNull(savedStateHandle.get<String>("docId"))
        docInteractors.getDoc(docId.toInt()).onEach { dataState ->
            if(dataState.data != null) {
                _uiState.value = DocDetailScreenState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()) {
                _uiState.value = DocDetailScreenState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }

}