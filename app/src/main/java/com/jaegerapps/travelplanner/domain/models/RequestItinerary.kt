package com.jaegerapps.travelplanner.domain.models

import android.content.Context
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.R


data class RequestItinerary(

    val days: String = "1",
    val location: String = "",
    val interests: String = "",
    val specialRequests: String = "",
    val transportation: Boolean = false,
    val preferredTransportation: String = "",
    val findRestaurants: Boolean = false,
    val mealTypes: List<MealType?> = emptyList(),
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
            val mealTypeList = emptyList<MealType>()
            if (findRestaurants && mealTypes.isNotEmpty()) {
                mealTypes.forEach {
                    if (it != null) {
                        val emptyList = MealType(
                            "",
                            ""
                        )
                        emptyList.copy(
                            meal = UiText.StringResource(
                                R.string.request_find_meal_type,
                                it.meal,
                                it.cuisine
                            )
                                .asString(context)
                        )

                        if (it.foodRequest?.isNotBlank() == true) {
                            emptyList.copy(
                                foodRequest = UiText.StringResource(
                                    R.string.request_find_meal_type_special_request,
                                    it.foodRequest
                                )
                                    .asString(context)
                            )
                        }
                        mealTypeList.plus(emptyList)

                    }
                }
            }
            val returnList =
                locationDuration + interests + specialRequests + transportation + restaurants

            mealTypeList.forEach {
                returnList.plus(it)
            }
            return returnList

        }
    }
}