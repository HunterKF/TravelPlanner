package com.jaegerapps.travelplanner.data.models.itineraryDTO

import com.squareup.moshi.Json

data class DayPlanDto(
    @field:Json(name = "day")
    val day: Int,
    @field:Json(name = "events")
    val events: Int,
    val plans: List<PlanDto>,
    val transportation: List<TransportationDto?>
)
