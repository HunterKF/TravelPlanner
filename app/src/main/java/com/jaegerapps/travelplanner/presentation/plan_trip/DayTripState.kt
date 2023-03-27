package com.jaegerapps.travelplanner.presentation.plan_trip

import com.jaegerapps.travelplanner.domain.models.RequestItinerary

data class DayTripState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val requestItinerary: RequestItinerary = RequestItinerary()
)
