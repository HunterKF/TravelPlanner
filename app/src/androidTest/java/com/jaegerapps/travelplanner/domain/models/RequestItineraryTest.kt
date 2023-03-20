package com.jaegerapps.travelplanner.domain.models

import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.jaegerapps.travelplanner.domain.models.RequestItinerary.Companion.toStringRequest
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.manipulation.Ordering.Context

class RequestItineraryTest {

    private lateinit var userItineraryRequest: RequestItinerary
    private lateinit var context: ContextCompat


    @Before
    fun setUp() {
        context = mockk<ContextCompat>(relaxed = true)
        userItineraryRequest = RequestItinerary(
            days = "1",
            location = "Paris, France",
            interests = "Interesting neighborhoods, cafes",
            specialRequests = "I want to have a nice cup of coffee looking at the Eiffel tower.",
            transportation = false,
            preferredTransportation = "",
            findRestaurants = true,
            mealTypes = listOf(
                MealType(
                    "lunch",
                    "local food, local cafe",
                    "Must have a croissant!"
                )
            )
        )
    }

}