package com.example.automobile.exceptions

class NoSessionException(
    override val message: String = "No Session Exception"
) : Exception() {
}