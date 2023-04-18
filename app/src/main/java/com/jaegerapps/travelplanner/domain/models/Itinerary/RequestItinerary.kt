package com.jaegerapps.travelplanner.domain.models.Itinerary

import android.content.Context
import android.util.Log
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.domain.models.google.PlaceInfo
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo


data class RequestItinerary(
    val days: String = "1",
    val aboutTrip: String = "",
    var places: List<PlaceInfo> = listOf(),
    var location: PlaceLocationInfo = PlaceLocationInfo(0.0, 0.0),
    var interests: List<String> = listOf(),
    val multiDay: Boolean = false,
    val specialRequests: List<SpecialRequest> = listOf(),
    val exclusionList: List<String?> = listOf<String>(),
) {
    companion object {
        fun RequestItinerary.toRequestString(context: Context): String {
            val locationQuery =
                UiText.StringResource(R.string.request_location_duration, location.location)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)

            var validLocations = listOf<String>(
                UiText.StringResource(R.string.list_of_valid_locations).asString(context),
            )

            places.forEach {
                validLocations = validLocations.plus(it.name)
//                validLocations = validLocations.plus(it.type.toString())
            }

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



            return locationQuery + aboutTrip + interests + validLocations + specialRequestsList + UiText.StringResource(
                R.string.ending_message
            ).asString(context)

        }
        fun RequestItinerary.toFilterString(context: Context): String {
            val locationQuery =
                UiText.StringResource(R.string.request_location_duration, location.location)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)

            var validLocations = listOf<String>(
                UiText.StringResource(R.string.list_of_valid_locations).asString(context),
            )

            places.forEach {
                validLocations = validLocations.plus(it.name)
//                validLocations = validLocations.plus(it.type.toString())
            }

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



            return locationQuery + aboutTrip + interests + validLocations + specialRequestsList + UiText.StringResource(
                R.string.ending_message_filter
            ).asString(context)

        }

        fun RequestItinerary.toMultiDayStringRequest(context: Context, currentDay: Int): String {
            val locationDuration =
                UiText.StringResource(R.string.request_location_duration, location.location)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)
            val tripDetails = ItineraryDetails.mapItineraryDetails(
                requests = specialRequests,
                days = days
            )
            val currentDayTripDetails = tripDetails.first { it.day == currentDay.toString() }
            var specialRequestsList = listOf<String>()
            var validLocations = listOf<String>(
                UiText.StringResource(R.string.list_of_valid_locations).asString(context),
            )
            places.forEach {
                validLocations = validLocations.plus(it.name)
//                validLocations = validLocations.plus(it.type.toString())
            }
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
                locationDuration + aboutTrip + interests + validLocations + specialRequestsList + exclusionListFormat


            return returnList

        }
    }
}