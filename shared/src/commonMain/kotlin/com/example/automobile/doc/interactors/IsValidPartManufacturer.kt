package com.example.automobile.doc.interactors

class IsValidPartManufacturer {
    operator fun invoke(manufacturer: String): Boolean{
        return manufacturer.length in 1..50
    }
}