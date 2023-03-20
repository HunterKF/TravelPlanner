package com.jaegerapps.travelplanner.domain.mappers

import com.google.common.truth.Truth.assertThat
import com.jaegerapps.travelplanner.domain.models.SinglePlan
import com.jaegerapps.travelplanner.domain.models.TransportationDetails

import org.junit.Before
import org.junit.Test

class MapPlansAndTransportKtTest {

    private lateinit var plans: List<SinglePlan>
    private lateinit var transport: List<TransportationDetails>
    private lateinit var expectedList: List<Any>

    @Before
    fun setUp() {
        plans = listOf(
            SinglePlan(
                "1234 Hemmingway",
                "Hunter's House",
                "Good place",
                "dank",
                "relaxation"
            ),
            SinglePlan(
                address = "0987 Peter St.",
                locationName = "Krez's House",
                description = "Yummy place",
                keywords = "spicy",
                type = "hot sauce"
            ),
            SinglePlan(
                address = "6785 Haeundae-ro",
                locationName = "Beach Paradise",
                description = "Beach side",
                keywords = "beach",
                type = "beach"
            )
        )

        transport = listOf(
            TransportationDetails(
                startingLocation = "Hunter's House",
                startingAddress = "1234 Hemmingway",
                endingLocation = "Krez's House",
                endingAddress = "0987 Peter St.",
                transportationType = "walking",
                commuteTime = 30,
                directions = "walk along Hemmingway north until you reach Peter St. Then turn right and you will arrive at Krez's House."
            ),
            TransportationDetails(
                startingLocation = "Krez's House",
                startingAddress = "0987 Peter St.",
                endingLocation = "Beach Paradise",
                endingAddress = "6785 Haeundae-ro",
                transportationType = "walking",
                commuteTime = 60,
                directions = "walk along Hemmingway north until you reach Peter St. Then turn right and you will arrive at Krez's House."
            )
        )
        expectedList = listOf(
            SinglePlan(
                "1234 Hemmingway",
                "Hunter's House",
                "Good place",
                "dank",
                "relaxation"
            ),
            TransportationDetails(
                startingLocation = "Hunter's House",
                startingAddress = "1234 Hemmingway",
                endingLocation = "Krez's House",
                endingAddress = "0987 Peter St.",
                transportationType = "walking",
                commuteTime = 30,
                directions = "walk along Hemmingway north until you reach Peter St. Then turn right and you will arrive at Krez's House."
            ),
            SinglePlan(
                address = "0987 Peter St.",
                locationName = "Krez's House",
                description = "Yummy place",
                keywords = "spicy",
                type = "hot sauce"
            ),
            TransportationDetails(
                startingLocation = "Krez's House",
                startingAddress = "0987 Peter St.",
                endingLocation = "Beach Paradise",
                endingAddress = "6785 Haeundae-ro",
                transportationType = "walking",
                commuteTime = 60,
                directions = "walk along Hemmingway north until you reach Peter St. Then turn right and you will arrive at Krez's House."
            ),
            SinglePlan(
                address = "6785 Haeundae-ro",
                locationName = "Beach Paradise",
                description = "Beach side",
                keywords = "beach",
                type = "beach"
            )
        )
    }

    @Test
    fun mapPlansAndTransport() {
        val mappedList = com.jaegerapps.travelplanner.domain.mappers.mapPlansAndTransport(
            plans = plans,
            transport = transport
        )
        println(mappedList)
        assertThat(mappedList).isEqualTo(expectedList)
    }
}