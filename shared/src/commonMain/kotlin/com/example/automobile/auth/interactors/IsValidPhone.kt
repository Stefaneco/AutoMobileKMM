package com.example.automobile.auth.interactors

class IsValidPhone {
    operator fun invoke(phone: String): Boolean {
        if(phone.length !in 6..17) return false
        if(!Regex("[0-9]+").matches(phone)) return false
        return true
    }
}