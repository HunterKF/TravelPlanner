package com.jaegerapps.travelplanner.domain.models

data class MealRequest(
    var day: Int = 1,
    var meal: MealTime,
    var cuisine: String,
    var foodRequest: String? = null,
    val formattedRequest: String = "",
    var id: Int
)