package com.jaegerapps.travelplanner.domain.models

import android.content.Context
import android.util.Log
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo


data class RequestItinerary(
    val days: String = "1",
    val aboutTrip: String = "",
    var places: List<String> = listOf<String>(),
    var location: PlaceLocationInfo = PlaceLocationInfo(0.0, 0.0),
    val interests: String = "",
    val multiDay: Boolean = false,
    val specialRequests: List<SpecialRequest> = listOf(),
    val exclusionList: List<String?> = listOf<String>(),
) {
    companion object {
        fun RequestItinerary.toStringRequest(context: Context): String {
            val locationQuery =
                UiText.StringResource(R.string.request_location_duration, location, days)
                    .asString(context)
            val interests =
                UiText.StringResource(R.string.request_interests, interests).asString(context)
            val aboutTrip = UiText.StringResource(R.string.about_trip, aboutTrip).asString(context)
            var validLocations = listOf<String>(
                UiText.StringResource(R.string.list_of_valid_locations).asString(context),
                places.toString()
            )

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



            val returnList =
                locationQuery + aboutTrip + interests + validLocations + specialRequestsList


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
                locationDuration + aboutTrip + interests + specialRequestsList + exclusionListFormat


            return returnList

        }
    }
}