package com.jaegerapps.travelplanner.domain.models

data class PlannedItinerary(
    val location: String,
    val durationOfStay: String,
    val interests: String,
    val dayPlan: DayPlan,
)