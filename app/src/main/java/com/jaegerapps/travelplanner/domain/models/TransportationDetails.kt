package com.jaegerapps.travelplanner.domain.models

data class TransportationDetails(
    val startingLocation: String,
    val startingAddress: String,
    val endingLocation: String,
    val endingAddress: String,
    val transportationType: String,
    val commuteTime: Int,
    val directions: String,
)
