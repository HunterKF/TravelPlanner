package com.jaegerapps.travelplanner.presentation

import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto

data class TestState (
    val isLoading: Boolean = false,
    val responseInfo: ResponseInfoDto? = null,
    val error: String? = null
)
