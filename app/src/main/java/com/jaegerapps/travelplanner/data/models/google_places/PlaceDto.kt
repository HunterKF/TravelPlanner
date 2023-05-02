package com.jaegerapps.travelplanner.data.models.google_places

import com.squareup.moshi.Json

data class PlaceDto(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "formatted_address")
    val address: String,
    @field:Json(name = "types")
    val types: List<String>,
    @field:Json(name = "photos")
    val photos: Array<PhotoReferenceDto>?
)

data class PhotoReferenceDto(
    @field:Json(name = "photo_reference")
    val photoReference: String
)