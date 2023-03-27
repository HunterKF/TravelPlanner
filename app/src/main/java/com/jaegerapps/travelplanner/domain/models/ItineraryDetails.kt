package com.jaegerapps.travelplanner.domain.models

data class ItineraryDetails(
    val day: String,
    val specialRequest: List<SpecialRequest>,
    val mealRequest: List<MealRequest?>,
) {
    companion object {
        fun mapItineraryDetails(
            meals: List<MealRequest?>,
            requests: List<SpecialRequest>,
            days: String,
        ): List<ItineraryDetails> {
            val totalDays = days.toInt()

            var returnList = listOf<ItineraryDetails>()
            for (i in 1..totalDays) {
                returnList = returnList.plus(
                    ItineraryDetails(
                        day = i.toString(),
                        mealRequest = meals.filter { it?.day == i },
                        specialRequest = requests.filter { it.day == i }
                    )
                )
            }
            return returnList
        }
    }
}
