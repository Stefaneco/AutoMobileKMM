package com.example.automobile.doc.interactors

class IsValidRegistration {
    operator fun invoke(registration: String) : Boolean{
        return registration.length in 1..30
    }
}