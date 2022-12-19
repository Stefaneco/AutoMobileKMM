package com.example.automobile.doc.interactors

data class DocInteractors (
    val getCarWithVin: GetCarWithVin,
    val getCustomerWithPhone: GetCustomerWithPhone,
    val isValidManufacturer: IsValidManufacturer,
    val isValidModel: IsValidModel,
    val isValidVin: IsValidVin,
    val isValidRegistration: IsValidRegistration,
    val isValidProblemDescription: IsValidProblemDescription,
    val createDoc: CreateDoc
        )