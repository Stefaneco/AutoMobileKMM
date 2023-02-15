package com.example.automobile.doc.interactors

class IsValidManufacturer {
    operator fun invoke(manufacturer: String) : Boolean{
        return manufacturer.length in 1..30
    }
}