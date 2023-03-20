package com.jaegerapps.travelplanner.presentation.singleTrip

import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.RequestItinerary

data class SingleTripState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val requestItinerary: RequestItinerary = RequestItinerary()
)
