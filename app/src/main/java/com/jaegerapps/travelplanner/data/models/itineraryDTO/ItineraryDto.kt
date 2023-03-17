package com.jaegerapps.travelplanner.data.models.itineraryDTO

import com.squareup.moshi.Json

data class ItineraryWrapperDto(
    @field:Json(name = "itinerary")
    val itinerary: ItineraryDto
)
data class ItineraryDto(
    @field:Json(name = "location")
    val location: String,
    @field:Json(name = "length")
    val length: Int,
    @field:Json(name = "interests")
    val interests: String,
    val day_plan: DayPlanDto
)
