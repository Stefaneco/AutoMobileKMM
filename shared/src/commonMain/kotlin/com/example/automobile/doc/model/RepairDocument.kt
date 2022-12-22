package com.example.automobile.doc.model

import com.example.automobile.profile.model.UserProfile
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RepairDocument(
    val id: Int,
    val startDate: String,
    val endDate: String,
    @SerialName("problem_description")
    val problemDescription: String,
    @SerialName("repair_description")
    val repairDescription: String,
    val mechanic: UserProfile,
    val customer: UserProfile,
    val car: Car,
    val parts: List<CarPart>
)