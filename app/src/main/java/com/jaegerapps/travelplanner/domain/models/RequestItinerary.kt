package com.jaegerapps.travelplanner.domain.models

import android.content.Context
import android.util.Log
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.R


data class RequestItinerary(
    val days: String = "1",
    val aboutTrip: String = "",
    val location: String = "",
    val interests: String = "",
    val multiDay: Boolean = false,
    val specialRequests: List<SpecialRequest> = listOf(),
    val transportation: Boolean = false,
    val preferredTransportation: PreferredTransport = PreferredTransport.Walking,
    val findRestaurants: Boolean = false,
    val mealRequests: List<MealRequest?> = emptyList(),
    val exclusionList: List<String?> = listOf<String>(),
) {
    companion object {
        fun RequestItinerary.toStringRequest(context: Context): String {
            val locationDuration =
                UiText.StringResource(R.string.request_location_duration, location, days)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)

            var specialRequestsList = listOf<String>()
            specialRequests.forEach {
                Log.d("RequestItinerary", "Current special request value: $it")
                if (it.request != "") {
                    specialRequestsList = specialRequestsList.plus(
                        UiText.StringResource(
                            R.string.request_special_requests,
                            it.request,
                            it.day
                        ).asString(context)
                    )

                } else {
                    "1234"
                }
            }


            val transportation =
                if (!transportation) UiText.StringResource(R.string.request_transportation_false)
                    .asString(context)
                else
                    UiText.StringResource(
                        R.string.request_transportation_true,
                        preferredTransportation.preference
                    ).asString(context)

            var restaurants = if (findRestaurants)
                UiText.StringResource(R.string.request_find_restaurants_true).asString(context)
            else
                UiText.StringResource(R.string.request_find_restaurants_false).asString(context)
            if (findRestaurants && mealRequests.isNotEmpty()) {
                mealRequests.forEach {
                    if (it != null) {
                        var formattedRequest = ""
                        formattedRequest = formattedRequest.plus(
                            UiText.StringResource(
                                R.string.request_find_meal_type,
                                it.day.toString(),
                                it.meal.type,
                                it.cuisine
                            )
                                .asString(context)
                        )


                        if (it.foodRequest != "") {
                            formattedRequest = formattedRequest.plus(
                                UiText.StringResource(
                                    R.string.request_find_meal_type_special_request,
                                    it.foodRequest!!
                                )
                                    .asString(context)
                            )
                        }
                        restaurants = restaurants.plus(
                            formattedRequest
                        )

                    }
                }
            }
            val returnList =
                locationDuration + aboutTrip + interests + specialRequestsList + restaurants + transportation


            return returnList

        }

        fun RequestItinerary.toMultiDayStringRequest(context: Context, currentDay: Int): String {
            val locationDuration =
                UiText.StringResource(R.string.request_location_duration, location)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)
            val tripDetails = ItineraryDetails.mapItineraryDetails(
                meals = mealRequests,
                requests = specialRequests,
                days = days
            )
            val currentDayTripDetails = tripDetails.first { it.day == currentDay.toString() }
            var specialRequestsList = listOf<String>()
            currentDayTripDetails.specialRequest.forEach {
                Log.d("RequestItinerary", "Current special request value: $it")
                if (it.request == "" && it.day == currentDay) {
                    specialRequestsList = specialRequestsList.plus(
                        UiText.StringResource(
                            R.string.request_special_requests,
                            it.request
                        ).asString(context)
                    )

                } else {
                    "1234"
                }
            }


            val transportation =
                if (!transportation) UiText.StringResource(R.string.request_transportation_false)
                    .asString(context)
                else
                    UiText.StringResource(
                        R.string.request_transportation_true,
                        preferredTransportation.preference
                    ).asString(context)

            var restaurants = if (findRestaurants)
                UiText.StringResource(R.string.request_find_restaurants_true).asString(context)
            else
                UiText.StringResource(R.string.request_find_restaurants_false).asString(context)
            if (findRestaurants && mealRequests.isNotEmpty()) {
                currentDayTripDetails.mealRequest.forEach {
                    if (it != null) {
                        var formattedRequest = ""
                        formattedRequest = formattedRequest.plus(
                            UiText.StringResource(
                                R.string.request_find_meal_type,
                                it.day.toString(),
                                it.meal.type,
                                it.cuisine
                            )
                                .asString(context)
                        )


                        if (it.foodRequest != "") {
                            formattedRequest = formattedRequest.plus(
                                UiText.StringResource(
                                    R.string.request_find_meal_type_special_request,
                                    it.foodRequest!!
                                )
                                    .asString(context)
                            )
                        }
                        restaurants = restaurants.plus(
                            formattedRequest
                        )

                    }
                }
            }
            var exclusionListFormat = listOf<String>()
            if (currentDay != 1) {
                exclusionListFormat = exclusionListFormat.plus(
                    UiText.StringResource(R.string.exclude_these_places).asString(context)
                )
                exclusionList.forEach {
                    it?.let {
                        exclusionListFormat = exclusionListFormat.plus(
                            it
                        )
                    }

                }
            }
            val returnList =
                locationDuration + aboutTrip + interests + specialRequestsList + restaurants + transportation + exclusionListFormat


            return returnList

        }
    }
}