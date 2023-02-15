package com.example.mechanicandroidapp.doc.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.automobile.doc.DocDetailScreenState
import com.example.automobile.doc.interactors.DocInteractors
import com.example.automobile.doc.model.CarPart
import com.example.automobile.doc.model.RepairDocument
import com.example.automobile.doc.model.UpdateDocRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EditDocumentViewModel @Inject constructor(
    private val docInteractors: DocInteractors,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DocDetailScreenState>(DocDetailScreenState.Loading)
    val uiState: StateFlow<DocDetailScreenState> = _uiState
    var isNavigatedOut = false

    val parts : MutableState<List<CarPart>> = mutableStateOf(listOf())
    val problemDescription : MutableState<String> = mutableStateOf("")
    val repairDescription : MutableState<String> = mutableStateOf("")

    private var startDoc : RepairDocument = RepairDocument.empty()

    init {
        val docId = checkNotNull(savedStateHandle.get<String>("docId"))
        docInteractors.getDoc(docId.toInt()).onEach { dataState ->
            if(dataState.data != null) {
                with(dataState){
                    startDoc = data!!
                    parts.value = data!!.parts as MutableList<CarPart>
                    problemDescription.value = data!!.problemDescription
                    repairDescription.value = data!!.repairDescription
                }
                _uiState.value = DocDetailScreenState.Success(dataState.data!!)
            }
            else if(!dataState.message.isNullOrEmpty()) {
                _uiState.value = DocDetailScreenState.Error(dataState.message!!)
            }
        }.launchIn(viewModelScope)
    }

    fun addPart(name: String, manufacturer: String, catalogNumber: String, isNew: Boolean){
        if(!isValidPart(name, manufacturer, catalogNumber)) return
        val partsCopy = parts.value.toMutableList()
        partsCopy.add(CarPart(name,manufacturer, catalogNumber, isNew))
        parts.value = partsCopy.toList()
    }

    fun removePart(part: CarPart) {
        val partsCopy = parts.value.toMutableList()
        partsCopy.remove(part)
        parts.value = partsCopy.toList()
    }

    fun saveChanges(isClosing: Boolean = false){
        if(wasDocEdited() || isClosing){
            docInteractors.updateDoc(
                UpdateDocRequest(
                    startDoc.id,
                    problemDescription.value,
                    repairDescription.value,
                    parts.value,
                    if(isClosing) { Timestamp.valueOf(LocalDateTime.now().toString()).time }
                        else 0))
                .onEach { dataState ->
                    if(dataState.isLoading){
                        _uiState.value = DocDetailScreenState.Loading
                    }
                    else if(!dataState.message.isNullOrEmpty()) {
                        _uiState.value = DocDetailScreenState.Error(dataState.message!!)
                    }
                    else {
                        _uiState.value = DocDetailScreenState.Saved
                    }
            }.launchIn(viewModelScope)
        }
        else {
            _uiState.value = DocDetailScreenState.NoChanges
        }
    }

    private fun wasDocEdited() : Boolean {
        return startDoc.repairDescription != repairDescription.value ||
                startDoc.problemDescription != problemDescription.value ||
                startDoc.parts != parts.value
    }

    fun isValidPartName(name: String): Boolean = docInteractors.isValidPartName(name)

    fun isValidPartManufacturer(manufacturer: String): Boolean = docInteractors.isValidPartManufacturer(manufacturer)

    fun isValidPartCatalogNumber(catalogNumber: String): Boolean = docInteractors.isValidPartCatalogNumber(catalogNumber)

    fun isValidProblemDescription(description: String) : Boolean = docInteractors.isValidProblemDescription(description)

    fun isValidRepairDescription(description: String) : Boolean = docInteractors.isValidRepairDescription(description)

    fun isValidPart(name: String, manufacturer: String, catalogNumber: String) : Boolean {
        return isValidPartName(name) && isValidPartManufacturer(manufacturer) && isValidPartCatalogNumber(catalogNumber)
    }
}