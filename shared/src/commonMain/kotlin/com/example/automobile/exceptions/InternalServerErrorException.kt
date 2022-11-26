package com.example.automobile.exceptions

class InternalServerErrorException(
    override val message: String = "Internal Server Error"
) : Exception() {
}