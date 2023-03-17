package com.jaegerapps.travelplanner.domain

import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.util.Resource

interface GptRepository {
    suspend fun getResponse(prompt: String): Resource<ResponseInfoDto>
    suspend fun sendSystemSpec(): Result<ResponseInfoDto>
}