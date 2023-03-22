package com.jaegerapps.travelplanner.domain.models

import org.junit.Before
import org.junit.runner.manipulation.Ordering.Context

class RequestItineraryTest {

    private lateinit var userItineraryRequest: RequestItinerary
    private lateinit var context: Context


    @Before
    fun setUp() {

        userItineraryRequest = RequestItinerary(
            days = "1",
            location = "Paris, France",
            interests = "Interesting neighborhoods, cafes",
            specialRequests = listOf(
                SpecialRequest(
                    id = 1,
                    day = 1,
                    request = "I want to have a nice cup of coffee looking at the Eiffel tower."
                )
            ),
            transportation = false,
            preferredTransportation = PreferredTransport.Walking,
            findRestaurants = true,
            mealRequests = listOf(
                MealRequest(
                    day = 1,
                    meal = MealTime.Dinner,
                    "cafe",
                    "Must have a croissant!",
                    id = 1
                )
            )
        )
    }



}