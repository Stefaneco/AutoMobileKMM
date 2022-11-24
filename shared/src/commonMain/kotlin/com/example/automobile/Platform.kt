package com.example.automobile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform