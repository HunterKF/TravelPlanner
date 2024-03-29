package com.jaegerapps.travelplanner.domain.models.Itinerary

data class SinglePlan(
    val address: String = "",
    var photoRef: String?,
    val locationName: String,
    val description: String,
    val keywords: String,
    val type: String,
)
