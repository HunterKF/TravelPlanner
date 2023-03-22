package com.jaegerapps.travelplanner.domain.models

import android.content.Context
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.R


data class RequestItinerary(
    val days: String = "1",
    val aboutTrip: String = "",
    val location: String = "",
    val interests: String = "",
    val specialRequests: List<SpecialRequest> = listOf(),
    val transportation: Boolean = false,
    val preferredTransportation: PreferredTransport = PreferredTransport.Walking,
    val findRestaurants: Boolean = false,
    val mealRequests: List<MealRequest?> = emptyList(),
    val exclusionList: List<String?> = emptyList(),
) {
    companion object {
        fun RequestItinerary.toStringRequest(context: Context): String {
            val locationDuration =
                UiText.StringResource(R.string.request_location_duration, location, days)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val specialRequests =
                if (specialRequests.isEmpty()) "" else UiText.StringResource(
                    R.string.request_special_requests,
                    specialRequests
                ).asString(context)
            val transportation =
                if (!transportation) UiText.StringResource(R.string.request_transportation_false)
                    .asString(context)
                else
                    UiText.StringResource(
                        R.string.request_transportation_true,
                        preferredTransportation
                    ).asString(context)

            val restaurants = if (findRestaurants)
                UiText.StringResource(R.string.request_find_restaurants_true).asString(context)
            else
                UiText.StringResource(R.string.request_find_restaurants_false).asString(context)
            val mealTypeList = emptyList<MealRequest>()
            if (findRestaurants && mealRequests.isNotEmpty()) {
                mealRequests.forEach {
                    if (it != null) {
                        val emptyList = MealRequest(
                            1,
                            MealTime.Dinner,
                            "",
                            id = 1
                        )
                        val formattedRequest = ""
                        emptyList.copy(
                            meal = it.meal,
                        )
                        formattedRequest.plus(
                            UiText.StringResource(
                                R.string.request_find_meal_type,
                                it.day.toString(),
                                it.meal.type,
                                it.cuisine
                            )
                                .asString(context)
                        )


                        if (it.foodRequest?.isNotBlank() == true) {
                            formattedRequest.plus(
                                UiText.StringResource(
                                    R.string.request_find_meal_type_special_request,
                                    it.foodRequest!!
                                )
                                    .asString(context)
                            )
                        }
                        emptyList.copy(
                            formattedRequest = formattedRequest
                        )
                        mealTypeList.plus(emptyList)

                    }
                }
            }
            val returnList =
                locationDuration + interests + specialRequests + transportation + restaurants

            mealTypeList.forEach {
                returnList.plus(it.formattedRequest)
            }
            return returnList

        }
    }
}