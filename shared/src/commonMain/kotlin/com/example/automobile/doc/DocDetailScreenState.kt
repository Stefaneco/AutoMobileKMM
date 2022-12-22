package com.example.automobile.doc

import com.example.automobile.doc.model.RepairDocument

sealed class DocDetailScreenState {
    object Loading : DocDetailScreenState()
    class Success(val doc : RepairDocument): DocDetailScreenState()
    class Error(val message: String): DocDetailScreenState()
}