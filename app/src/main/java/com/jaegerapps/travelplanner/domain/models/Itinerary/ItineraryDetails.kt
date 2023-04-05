package com.jaegerapps.travelplanner.domain.models.Itinerary

data class ItineraryDetails(
    val day: String,
    val specialRequest: List<SpecialRequest>,
) {
    companion object {
        fun mapItineraryDetails(
            requests: List<SpecialRequest>,
            days: String,
        ): List<ItineraryDetails> {
            val totalDays = days.toInt()

            var returnList = listOf<ItineraryDetails>()
            for (i in 1..totalDays) {
                returnList = returnList.plus(
                    ItineraryDetails(
                        day = i.toString(),
                        specialRequest = requests.filter { it.day == i }
                    )
                )
            }
            return returnList
        }
    }
}
