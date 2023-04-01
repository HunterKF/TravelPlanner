package com.jaegerapps.travelplanner.domain.models.google


data class GooglePrediction(
    var predictions: List<GooglePredictionTerm>
)
data class GooglePredictionTerm(
    var name: String = "",
    var placeId: String = "",
)
