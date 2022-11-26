package com.example.automobile.exceptions

class TooManyRequestsException(
    override val message: String = "Too many requests. Try again later."
) : Exception() {
}