package com.jaegerapps.travelplanner.data.models.google_places

import com.squareup.moshi.Json

data class AutoCompleteDto(
    @field:Json(name = "predictions")
    val predictions: List<PredictionPlaceDto>
)

data class PredictionPlaceDto(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "place_id")
    val placeId: String
)
