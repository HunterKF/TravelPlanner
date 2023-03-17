package com.jaegerapps.travelplanner.data.mappers

import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ItineraryWrapperDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary
import com.jaegerapps.travelplanner.domain.models.SinglePlan
import com.jaegerapps.travelplanner.domain.models.TransportationDetails
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

fun ResponseDto.toResponseInfo(): ResponseInfoDto {
    val gson = Gson()
    val responseInfo = gson.fromJson(
        this.choices[0]?.message?.content?.replace("\\", ""),
        ItineraryWrapperDto::class.java
    )

    return ResponseInfoDto(
        itinerary = responseInfo
    )
}

fun ResponseInfoDto.toItinerary(): Itinerary {
    val dtoInfo = this.itinerary.itinerary
    val dayPlanDto = this.itinerary.itinerary.day_plan
    val plansDto = this.itinerary.itinerary.day_plan.plans
    val plans = emptyList<SinglePlan>()
    val transportation = emptyList<TransportationDetails>()

    plansDto.forEach { plan ->
        plans.plus(
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

    if (transportationDto.isNotEmpty()) {
        transportationDto.forEach { info ->
            transportationDto.plus(
                TransportationDetails(
                    startingLocation = info!!.starting,
                    startingAddress = info.starting_address,
                    endingLocation = info.ending,
                    endingAddress = info.ending_address,
                    transportationType = info.type_of_transportation,
                    commuteTime = info.commute_duration,
                    directions = info.directions
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
    return Itinerary(
        location = dtoInfo.location,
        durationOfStay = dtoInfo.length,
        interests = dtoInfo.interests,
        dayPlan = dayPlan
    )
}

fun String.toJsonObject(): RequestBody {

    val data = JSONObject()
    data.put("model", "gpt-3.5-turbo")
    data.put("messages", this)
    data.put("max_tokens", 4000)
    data.put("temperature", 1.0)

    return data.toString().toRequestBody("application/json".toMediaTypeOrNull())
}


