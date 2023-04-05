package com.jaegerapps.travelplanner.domain.models.Itinerary

data class MealRequest(
    var day: Int = 1,
    var meal: MealTime,
    var cuisine: String,
    var foodRequest: String = "",
    val formattedRequest: String = "",
    var id: Int
)