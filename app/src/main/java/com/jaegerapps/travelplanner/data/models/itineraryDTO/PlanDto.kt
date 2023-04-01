package com.jaegerapps.travelplanner.data.models.itineraryDTO

import com.squareup.moshi.Json

data class PlanDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "keywords")
    val keywords: String,
    @field:Json(name = "type")
    val type: String,
)
