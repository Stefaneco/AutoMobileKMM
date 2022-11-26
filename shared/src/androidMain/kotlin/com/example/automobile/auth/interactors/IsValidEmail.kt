package com.example.automobile.auth.interactors

import android.util.Patterns

actual class IsValidEmail {
    operator fun invoke(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}