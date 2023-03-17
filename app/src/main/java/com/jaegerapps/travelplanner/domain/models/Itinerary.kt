package com.jaegerapps.travelplanner.domain.models

data class Itinerary(
        val location: String,
        val durationOfStay: Int,
        val interests: String,
        val dayPlan: DayPlan,
)