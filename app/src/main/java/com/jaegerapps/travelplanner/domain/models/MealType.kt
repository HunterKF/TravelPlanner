package com.jaegerapps.travelplanner.domain.models

data class MealType(
    val meal: String,
    val cuisine: String,
    val foodRequest: String? = null,
)