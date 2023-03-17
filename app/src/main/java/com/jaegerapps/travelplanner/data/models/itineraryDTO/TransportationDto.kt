package com.jaegerapps.travelplanner.data.models.itineraryDTO

import com.squareup.moshi.Json

data class TransportationDto (
    @field:Json(name = "starting")
    val starting: String,
    @field:Json(name = "starting_address")
    val starting_address: String,
    @field:Json(name = "ending")
    val ending: String,
    @field:Json(name = "ending_address")
    val ending_address: String,
    @field:Json(name = "type_of_transportation")
    val type_of_transportation: String,
    @field:Json(name = "commute_duration")
    val commute_duration: Int,
    @field:Json(name = "directions")
    val directions: String
)
