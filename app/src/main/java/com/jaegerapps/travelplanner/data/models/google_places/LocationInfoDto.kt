package com.jaegerapps.travelplanner.data.models.google_places

import com.squareup.moshi.Json


data class LocationResultWrapperDto(
    @field:Json(name = "result")
    val result: GeometryWrapperDto,
)

data class GeometryWrapperDto(
    @field:Json(name = "geometry")
    val geometry: LocationWrapperDto,
)

data class LocationWrapperDto(
    @field:Json(name = "location")
    val location: LocationDto,
)

data class LocationDto(
    @field:Json(name = "lat")
    val lat: Double,
    @field:Json(name = "lng")
    val lng: Double,
)