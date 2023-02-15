package com.example.automobile.doc

import com.example.automobile.doc.model.Car

sealed class CarState {
    object Loading : CarState()
    object Static : CarState()
    class Success(val car: Car): CarState()
    class Error(val message: String): CarState()
}