package com.jaegerapps.travelplanner.domain.models.google

data class PlaceLocationInfo(
    val lat: Double,
    val long: Double,
    var location: String = ""
)
