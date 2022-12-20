package com.example.automobile.doc

import com.example.automobile.doc.model.DocListItem

sealed class DocListScreenState {
    object Loading : DocListScreenState()
    class Success(val docListItems : List<DocListItem>): DocListScreenState()
    class Error(val message: String): DocListScreenState()
}