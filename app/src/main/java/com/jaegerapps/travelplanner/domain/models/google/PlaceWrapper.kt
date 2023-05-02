package com.jaegerapps.travelplanner.domain.models.google

data class PlaceWrapper(
    val nextPage: String?,
    val places: List<PlaceInfo> = listOf()
)

data class PlaceInfo(
    val name: String,
    val photoReference: String?
)