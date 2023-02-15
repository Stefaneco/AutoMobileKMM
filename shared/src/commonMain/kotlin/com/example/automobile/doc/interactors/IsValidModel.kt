package com.example.automobile.doc.interactors

class IsValidModel {
    operator fun invoke(model: String) : Boolean{
        return model.length in 1..50
    }
}