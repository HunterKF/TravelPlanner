package com.jaegerapps.travelplanner.presentation.plan_trip

import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction

data class DayTripState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val predictions: GooglePrediction = GooglePrediction(emptyList()),
    var requestItinerary: RequestItinerary = RequestItinerary(),

    )
