package com.jaegerapps.travelplanner.data.models.google_places

import com.squareup.moshi.Json

data class PlaceResultDto(
    @field:Json(name = "next_page_token")
    val nextPage: String?,
    @field:Json(name = "results")
    val places: List<PlaceDto>
)

