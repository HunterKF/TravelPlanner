package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.gpt.GptFilterPlace

interface GptRepository {
    suspend fun getResponse(prompt: String): Result<PlannedItinerary>
    suspend fun filterLocations(prompt: String): Result<GptFilterPlace>
    suspend fun sendSystemSpec(): Result<ResponseInfoDto>
}