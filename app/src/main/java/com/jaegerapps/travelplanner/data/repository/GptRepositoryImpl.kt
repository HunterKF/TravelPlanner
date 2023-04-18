package com.jaegerapps.travelplanner.data

import android.util.Log
import com.jaegerapps.travelplanner.data.mappers.*
import com.jaegerapps.travelplanner.data.models.gpt.GptMessageSend
import com.jaegerapps.travelplanner.data.models.gpt.GptModelSendDto
import com.jaegerapps.travelplanner.data.remote.GptApi
import com.jaegerapps.travelplanner.domain.repositories.GptRepository
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.gpt.GptFilterPlace
import javax.inject.Inject


class GptRepositoryImpl @Inject constructor(
    private val api: GptApi,
) : GptRepository {
    override suspend fun getResponse(prompt: String): Result<PlannedItinerary> {
        Log.d("getResponse", "Get response is starting. ")
        return try {
            val requestBody = GptModelSendDto(
                messages =
                GptMessageSend.baseRequestList.plus(
                    GptMessageSend(
                        role = "user",
                        content = prompt
                    ),
                )
            ).toRequestBody()

            val response = api.getItineraryResponse(requestBody)

            val assistantMessage = response.choices[0]?.message?.content

            if (assistantMessage != null) {
                if (assistantMessage.contains("Sorry") || assistantMessage.contains("error") || assistantMessage.contains("missing")) {
                    // Handle the error appropriately
                    Result.failure(Exception("Error detected: $assistantMessage"))
                } else {
                    Result.success(
                        response.toResponseInfoDto().toPlannedItinerary()
                    )
                }
            } else {
                Result.failure(Exception("Error detected: $response"))

            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }


    override suspend fun filterLocations(prompt: String): Result<GptFilterPlace> {
        Log.d("getResponse", "Get response is starting. ")
        return try {
            Result.success(
                GptModelSendDto(
                    messages =
                    GptMessageSend.baseFilterList.plus(
                        GptMessageSend(
                            role = "user",
                            content = prompt
                        ),
                    )
                ).toRequestBody()
                    .let {
                        Log.d("getResponse", "Get response is now in let. ")
                        api.filterList(it)
                            .toGptFilterPlaceDto()
                            .toGptFilterPlace()

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
                GptModelSendDto(messages = GptMessageSend.baseRequestList).toRequestBody()
                    .let { api.sendSystemSpec(it).toResponseInfoDto() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

