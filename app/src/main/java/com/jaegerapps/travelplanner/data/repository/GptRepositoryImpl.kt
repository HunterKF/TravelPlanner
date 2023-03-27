package com.jaegerapps.travelplanner.data

import android.util.Log
import com.jaegerapps.travelplanner.data.mappers.toPlannedItinerary
import com.jaegerapps.travelplanner.data.mappers.toJson
import com.jaegerapps.travelplanner.data.mappers.toResponseInfoDto
import com.jaegerapps.travelplanner.data.models.GptMessageSend
import com.jaegerapps.travelplanner.data.models.GptModelSend
import com.jaegerapps.travelplanner.data.remote.GptApi
import com.jaegerapps.travelplanner.domain.GptRepository
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.PlannedItinerary
import javax.inject.Inject


class GptRepositoryImpl @Inject constructor(
    private val api: GptApi,
) : GptRepository {
    override suspend fun getResponse(prompt: String): Result<PlannedItinerary> {
        Log.d("getResponse", "Get response is starting. ")
        return try {
            Result.success(
                GptModelSend(
                    messages =
                    GptMessageSend.baseSpecList.plus(
                        GptMessageSend(
                            role = "user",
                            content = prompt
                        ),
                    )
                ).toJson()
                    .let {
                        Log.d("getResponse", "Get response is now in let. ")
                        api.getResponse(it)
                            .toResponseInfoDto()
                            .toPlannedItinerary()

                    }
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun sendSystemSpec(): Result<ResponseInfoDto> {
        return try {
            Result.success(
                GptModelSend(messages = GptMessageSend.baseSpecList).toJson()
                    .let { api.sendSystemSpec(it).toResponseInfoDto() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

