package com.jaegerapps.travelplanner.data.mappers

import android.util.Log
import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.gpt.GptFilterPlaceDto
import com.jaegerapps.travelplanner.data.models.gpt.GptModelSendDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ItineraryWrapperDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ResponseInfoDto
import com.jaegerapps.travelplanner.domain.models.Itinerary.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.domain.models.gpt.GptFilterPlace
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

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
    println("ResponseInfoDto: $this")
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
                type = plan.type,
                photoRef = null
            )
        )
    }



    val dayPlan = DayPlan(
        plannedDay = dayPlanDto.day.toInt(),
        numberOfEvents = dayPlanDto.events,
        planList = plans,
        loaded = true
    )
    println(dayPlan)
    return PlannedItinerary(
        location = dtoInfo.location,
        interests = dtoInfo.interests,
        dayPlan = dayPlan
    )
}

fun ResponseDto.toGptFilterPlaceDto(): GptFilterPlaceDto {
    Log.d("MultiDay", "toGptFilterPlaceDto: $this")
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


fun GptModelSendDto.toRequestBody(): RequestBody {
    val json = Gson().toJson(this)
    return json.toString().toRequestBody("application/json".toMediaTypeOrNull())
}



