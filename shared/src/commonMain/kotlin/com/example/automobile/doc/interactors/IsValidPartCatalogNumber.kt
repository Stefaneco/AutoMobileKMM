package com.example.automobile.doc.interactors

class IsValidPartCatalogNumber {
    operator fun invoke(catalogNumber: String): Boolean{
        return catalogNumber.length in 1..50
    }
}