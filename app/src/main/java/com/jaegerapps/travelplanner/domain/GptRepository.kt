package com.jaegerapps.travelplanner.domain

import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.PlannedItinerary

interface GptRepository {
    suspend fun getResponse(prompt: String): Result<PlannedItinerary>
    suspend fun sendSystemSpec(): Result<ResponseInfoDto>
}