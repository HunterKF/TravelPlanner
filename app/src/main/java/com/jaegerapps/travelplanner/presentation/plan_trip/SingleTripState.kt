package com.jaegerapps.travelplanner.presentation.plan_trip

import com.jaegerapps.travelplanner.domain.models.RequestItinerary

data class SingleTripState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val requestItinerary: RequestItinerary = RequestItinerary()
)
