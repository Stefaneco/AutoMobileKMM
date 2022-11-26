package com.example.automobile.auth.interactors

class IsValidName {
    operator fun invoke(surname: String): Boolean{
        return surname.length in 2..50
    }
}