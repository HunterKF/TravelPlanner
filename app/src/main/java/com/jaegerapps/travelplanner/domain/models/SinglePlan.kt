package com.jaegerapps.travelplanner.domain.models

data class SinglePlan(
    val address: String,
    val locationName: String,
    val description: String,
    val keywords: String,
    val type: String,
)
