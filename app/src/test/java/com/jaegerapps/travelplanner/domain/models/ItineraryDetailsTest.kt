package com.jaegerapps.travelplanner.domain.models

import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test

class ItineraryDetailsTest {

    private lateinit var specialRequest: List<SpecialRequest>
    private lateinit var mealRequest: List<MealRequest?>
    private lateinit var days: String
    private lateinit var itineraryDetails: List<ItineraryDetails>
    @Before
    fun setUp() {
        specialRequest = listOf(
            SpecialRequest(
                id = 1,
                day = 2,
                request = "This is request 1."
            ),
            SpecialRequest(
                id = 1,
                day = 1,
                request = "This is request 2."
            ),
            SpecialRequest(
                id = 1,
                day = 4,
                request = "This is request 3."
            ),
            SpecialRequest(
                id = 1,
                day = 2,
                request = "This is request 4."
            ),
            SpecialRequest(
                id = 1,
                day = 1,
                request = "This is request 5."
            ),
        )
        mealRequest = listOf(
            MealRequest(
                id = 1,
                day = 1,
                meal = MealTime.Dinner,
                cuisine = "French food",
                foodRequest = "This is food request 1",
            ),
            MealRequest(
                id = 2,
                day = 1,
                meal = MealTime.Lunch,
                cuisine = "Korean food",
                foodRequest = "This is food request 2",
            ),
            MealRequest(
                id = 3,
                day = 3,
                meal = MealTime.Breakfast,
                cuisine = "MExican food",
                foodRequest = "This is food request 3",
            ),
            MealRequest(
                id = 4,
                day = 3,
                meal = MealTime.Dinner,
                cuisine = "German food",
                foodRequest = "This is food request 4",
            ),
            MealRequest(
                id = 5,
                day = 2,
                meal = MealTime.Breakfast,
                cuisine = "Irish food",
                foodRequest = "This is food request 5",
            ),
        )
        days = "4"
        itineraryDetails = listOf(
            ItineraryDetails(
                day = "1",
                specialRequest = listOf(
                    SpecialRequest(
                        id = 1,
                        day = 1,
                        request = "This is request 2."
                    ),
                    SpecialRequest(
                        id = 1,
                        day = 1,
                        request = "This is request 5."
                    )
                ),
                mealRequest = listOf(
                    MealRequest(
                        id = 1,
                        day = 1,
                        meal = MealTime.Dinner,
                        cuisine = "French food",
                        foodRequest = "This is food request 1",
                    ),
                    MealRequest(
                        id = 2,
                        day = 1,
                        meal = MealTime.Lunch,
                        cuisine = "Korean food",
                        foodRequest = "This is food request 2",
                    )
                )
            ),
            ItineraryDetails(
                day = "2",
                specialRequest = listOf(
                    SpecialRequest(
                        id = 1,
                        day = 2,
                        request = "This is request 1."
                    ),
                    SpecialRequest(
                        id = 1,
                        day = 2,
                        request = "This is request 4."
                    ),
                ),
                mealRequest = listOf(
                    MealRequest(
                        id = 5,
                        day = 2,
                        meal = MealTime.Breakfast,
                        cuisine = "Irish food",
                        foodRequest = "This is food request 5",
                    )
                )
            ),
            ItineraryDetails(
                day = "3",
                specialRequest = listOf(),
                mealRequest = listOf(
                    MealRequest(
                        id = 3,
                        day = 3,
                        meal = MealTime.Breakfast,
                        cuisine = "MExican food",
                        foodRequest = "This is food request 3",
                    ),
                    MealRequest(
                        id = 4,
                        day = 3,
                        meal = MealTime.Dinner,
                        cuisine = "German food",
                        foodRequest = "This is food request 4",
                    ),
                )
            ),
            ItineraryDetails(
                day = "4",
                specialRequest = listOf(
                    SpecialRequest(
                        id = 1,
                        day = 4,
                        request = "This is request 3."
                    ),
                ),
                mealRequest = listOf()
            ),
        )

    }

    @Test
    fun `Map raw data to itinerary details`() {
        val mappedList = ItineraryDetails.mapItineraryDetails(
            meals = mealRequest,
            requests = specialRequest,
            days = days
        )

        for (i in 1..mappedList.size) {
            val index = i - 1
            assertThat(mappedList[index].day).isEqualTo(itineraryDetails[index].day)
            assertThat(mappedList[index].mealRequest).isEqualTo(itineraryDetails[index].mealRequest)
            assertThat(mappedList[index].specialRequest).isEqualTo(itineraryDetails[index].specialRequest)

        }
    }
}