package com.example.automobile.doc.model

import com.example.automobile.profile.model.UserProfile
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class RepairDocument(
    var id: Int,
    val startDate: String,
    val endDate: String,
    @SerialName("problem_description")
    var problemDescription: String,
    @SerialName("repair_description")
    var repairDescription: String,
    val mechanic: UserProfile,
    val customer: UserProfile,
    val car: Car,
    val parts: List<CarPart>
) {
    companion object {
        fun empty(): RepairDocument {
            return RepairDocument(
                0, "", "", "", "",
                UserProfile("", "", "", ""),
                UserProfile("", "", "", ""),
                Car("", "", "", "", 0),
                listOf()
            )
        }
    }
}