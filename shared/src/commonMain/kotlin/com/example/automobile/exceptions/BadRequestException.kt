package com.example.automobile.exceptions

class BadRequestException(
    override val message: String = "Bad Request"
) : Exception() {
}