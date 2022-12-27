package com.example.automobile.doc.interactors

class IsValidPartName {
    operator fun invoke(partName: String): Boolean{
        return partName.length in 1..50
    }
}