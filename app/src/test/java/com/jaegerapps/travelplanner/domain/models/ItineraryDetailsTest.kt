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

            ),
            ItineraryDetails(
                day = "3",
                specialRequest = listOf(),

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
            ),
        )

    }

    @Test
    fun `Map raw data to itinerary details`() {
        val mappedList = ItineraryDetails.mapItineraryDetails(
            requests = specialRequest,
            days = days
        )

        for (i in 1..mappedList.size) {
            val index = i - 1
            assertThat(mappedList[index].day).isEqualTo(itineraryDetails[index].day)
            assertThat(mappedList[index].specialRequest).isEqualTo(itineraryDetails[index].specialRequest)

        }
    }
}