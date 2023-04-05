package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.Itinerary.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.TransportationDetails
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.PlanContainer
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.ShowDay
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.TripTopBar

@Composable
fun MyTripScreen(
    viewModel: SharedViewModel,
) {
    val spacing = LocalSpacing.current
    val plan = viewModel._plannedItinerary.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(spacing.spaceMedium)

    ) {
        TripTopBar(title = plan.location)
        Spacer(modifier = Modifier.size(spacing.spaceMedium))
        ShowDay(duration = plan.durationOfStay, currentDay = viewModel.currentDay)
        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (plan.multiTrip && plan.multiDayPlan.isNotEmpty()) {
                val currentPlan =
                    plan.multiDayPlan.first { it.currentDay.toInt() == viewModel.currentDay.value }
                currentPlan.planList.forEach { item ->
                    item {
                        PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                    }
                }
            } else {
                plan.dayPlan.planList.forEach { item ->
                    item {
                        PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyTripPreview() {
    val viewModel = SharedViewModel()
    val day1Plan = listOf(
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
    val day2Plan = listOf(
        SinglePlan(
            "12313",
            "Haeundae",
            "Good place",
            "dank",
            "relaxation"
        ),
        SinglePlan(
            address = "123123123.",
            locationName = "Seoul",
            description = "Yummy place",
            keywords = "spicy",
            type = "hot sauce"
        ),
        SinglePlan(
            address = "asdasdaso",
            locationName = "JANGSANG",
            description = "Beach side",
            keywords = "beach",
            type = "beach"
        )
    )

    val transport = listOf(
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
    viewModel._plannedItinerary.value = PlannedItinerary(
        "Berlin, Germany",
        "5",
        "pop music and beer gardens",
        dayPlan =
        DayPlan(
            currentDay = "1",
            numberOfEvents = 1,
            planList = day1Plan,
        ),
        multiTrip = true,
        multiDayPlan = listOf(
            DayPlan(
                currentDay = "1",
                numberOfEvents = 3,
                planList = day1Plan,
            ),
            DayPlan(
                currentDay = "2",
                numberOfEvents = 3,
                planList = day2Plan,
            ),
            DayPlan(
                currentDay = "3",
                numberOfEvents = 3,
                planList = day1Plan,
            ),
            DayPlan(
                currentDay = "4",
                numberOfEvents = 3,
                planList = day1Plan,
            ),
            DayPlan(
                currentDay = "5",
                numberOfEvents = 3,
                planList = day1Plan,
            ),
        )

    )
    MyTripScreen(viewModel = viewModel)
}