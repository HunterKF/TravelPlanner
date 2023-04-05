package com.jaegerapps.travelplanner.domain.models.Itinerary

data class PlannedItinerary(
    val location: String,
    var durationOfStay: String = "1",
    val interests: String,
    val multiTrip: Boolean = false,
    var dayPlan: DayPlan,
    var multiDayPlan: List<DayPlan> = listOf<DayPlan>(),
)