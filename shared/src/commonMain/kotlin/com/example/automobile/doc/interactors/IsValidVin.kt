package com.example.automobile.doc.interactors

class IsValidVin {
    operator fun invoke(manufacturer: String) : Boolean{
        if(Regex("[QOI]") in manufacturer) return false
        return manufacturer.length == 17
    }
}