package com.jaegerapps.travelplanner.data.mappers

import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.GptModelSend
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ItineraryWrapperDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

fun ResponseDto.toResponseInfoDto(): ResponseInfoDto {
    val gson = Gson()
    val responseInfo = gson.fromJson(
        this.choices[0]?.message?.content?.replace("\\", ""),
        ItineraryWrapperDto::class.java
    )

    return ResponseInfoDto(
        itinerary = responseInfo
    )
}

fun ResponseInfoDto.toItinerary(): PlannedItinerary {
    val dtoInfo = this.itinerary.itinerary
    val dayPlanDto = this.itinerary.itinerary.day_plan
    val plansDto = this.itinerary.itinerary.day_plan.plans
    val plans = arrayListOf<SinglePlan>()
    val transportation = arrayListOf<TransportationDetails>()

    plansDto.forEach { plan ->
        plans.add(
            SinglePlan(
                address = plan.address,
                locationName = plan.name,
                description = plan.description,
                keywords = plan.keywords,
                type = plan.type
            )
        )
    }
    val transportationDto = this.itinerary.itinerary.day_plan.transportation
    println("Current transportationDto $transportationDto")

    if (transportationDto.isNotEmpty()) {
        transportationDto.forEach { info ->
            transportation.add(
                TransportationDetails(
                    startingLocation = info?.starting ?: "",
                    startingAddress = info?.starting_address ?: "",
                    endingLocation = info?.ending ?: "",
                    endingAddress = info?.ending_address ?: "",
                    transportationType = info?.type_of_transportation ?: "",
                    commuteTime = info?.commute_duration ?: 12,
                    directions = info?.directions ?: ""
                )
            )
        }
    }

    val dayPlan = DayPlan(
        currentDay = dayPlanDto.day,
        numberOfEvents = dayPlanDto.events,
        planList = plans,
        transportationDetails = transportation
    )
    println(dayPlan)
    return PlannedItinerary(
        location = dtoInfo.location,
        durationOfStay = dtoInfo.length.toString(),
        interests = dtoInfo.interests,
        dayPlan = dayPlan
    )
}

fun RequestItinerary.toJson(): String? {
    return Gson().toJson(this)
}

fun GptModelSend.toJson(): RequestBody {
    val json = Gson().toJson(this)
    return json.toString().toRequestBody("application/json".toMediaTypeOrNull())
}

fun String.requestBody(): RequestBody {

    val data = JSONObject()
    data.put("model", "gpt-3.5-turbo")
    data.put("messages", this)
    data.put("max_tokens", 4000)
    data.put("temperature", 1.0)

    return data.toString().toRequestBody("application/json".toMediaTypeOrNull())
}


