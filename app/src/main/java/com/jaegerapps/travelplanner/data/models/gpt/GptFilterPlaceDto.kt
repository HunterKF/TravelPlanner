package com.jaegerapps.travelplanner.data.models.gpt

import com.squareup.moshi.Json

data class GptFilterPlaceDto(
    @field:Json(name = "places")
    val places: List<String>,
)