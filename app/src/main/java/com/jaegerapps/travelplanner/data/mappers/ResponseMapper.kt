package com.jaegerapps.travelplanner.data.mappers

import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.gpt.GptFilterPlaceDto
import com.jaegerapps.travelplanner.data.models.gpt.GptModelSendDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ItineraryWrapperDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.Itinerary.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.domain.models.gpt.GptFilterPlace
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

fun ResponseInfoDto.toPlannedItinerary(): PlannedItinerary {
    val dtoInfo = this.itinerary.itinerary
    val dayPlanDto = this.itinerary.itinerary.day_plan
    val plansDto = this.itinerary.itinerary.day_plan.plans
    val plans = arrayListOf<SinglePlan>()

    plansDto.forEach { plan ->
        plans.add(
            SinglePlan(
                locationName = plan.name,
                description = plan.description,
                keywords = plan.keywords,
                type = plan.type
            )
        )
    }



    val dayPlan = DayPlan(
        currentDay = dayPlanDto.day,
        numberOfEvents = dayPlanDto.events,
        planList = plans,
    )
    println(dayPlan)
    return PlannedItinerary(
        location = dtoInfo.location,
        interests = dtoInfo.interests,
        dayPlan = dayPlan
    )
}

fun ResponseDto.toGptFilterPlaceDto(): GptFilterPlaceDto {
    val gson = Gson()
    val responseInfo = gson.fromJson(
        this.choices[0]?.message?.content?.replace("\\", ""),
        GptFilterPlaceDto::class.java
    )
    return responseInfo
}

fun GptFilterPlaceDto.toGptFilterPlace(): GptFilterPlace {
    return GptFilterPlace(
        places
    )
}

fun RequestItinerary.toJson(): String? {
    return Gson().toJson(this)
}

fun GptModelSendDto.toJson(): RequestBody {
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


