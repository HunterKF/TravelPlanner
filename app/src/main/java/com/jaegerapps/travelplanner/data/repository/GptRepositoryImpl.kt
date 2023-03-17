package com.jaegerapps.travelplanner.data

import com.jaegerapps.travelplanner.data.mappers.toJsonObject
import com.jaegerapps.travelplanner.data.mappers.toResponseInfo
import com.jaegerapps.travelplanner.data.models.GptMessageSend
import com.jaegerapps.travelplanner.data.models.GptModelSend
import com.jaegerapps.travelplanner.data.models.toJson
import com.jaegerapps.travelplanner.data.remote.GptApi
import com.jaegerapps.travelplanner.domain.GptRepository
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.util.Resource
import javax.inject.Inject


class GptRepositoryImpl @Inject constructor(
    private val api: GptApi,
) : GptRepository {
    override suspend fun getResponse(prompt: String): Resource<ResponseInfoDto> {

        return try {
            Resource.Success(
                data = prompt.toJsonObject().let { api.getResponse(it).toResponseInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun sendSystemSpec(): Result<ResponseInfoDto> {
        return try {
            Result.success(
                GptModelSend(messages = GptMessageSend.baseSpecList).toJson()
                    .let { api.sendSystemSpec(it).toResponseInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

