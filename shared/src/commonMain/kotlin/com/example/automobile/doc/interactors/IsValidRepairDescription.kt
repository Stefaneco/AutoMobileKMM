package com.example.automobile.doc.interactors

class IsValidRepairDescription {
    operator fun invoke(description: String) : Boolean {
        return description.length in 0 .. 2000
    }
}