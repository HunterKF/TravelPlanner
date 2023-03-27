package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.mappers.mapPlansAndTransport
import com.jaegerapps.travelplanner.domain.models.DayPlan
import com.jaegerapps.travelplanner.domain.models.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.SinglePlan
import com.jaegerapps.travelplanner.domain.models.TransportationDetails
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.PlanContainer
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.ShowDay
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.TransportContainer
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

            plan.dayPlan.planAndTransport.forEach {item ->
                when (item) {
                    is SinglePlan -> {
                        item {
                            PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                        }
                    }
                    is TransportationDetails -> {
                        item {
                            TransportContainer(
                                transport = item,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            /*item(plan.dayPlan) {
                if (plan.dayPlan.currentDay.toInt() == viewModel.currentDay.value) {
                    plan.dayPlan.planAndTransport.forEach { item ->
                        when (item) {
                            is SinglePlan -> {
                                PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                            }
                            is TransportationDetails -> {
                                TransportContainer(
                                    transport = item,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }*/

        }
    }
}

@Preview
@Composable
fun MyTripPreview() {
    val viewModel = SharedViewModel()
    val plans = listOf(
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
        "1",
        "pop music and beer gardens",
        dayPlan =
            DayPlan(
                currentDay = "1",
                numberOfEvents = 1,
                planList = plans,
                transportationDetails = transport,
                planAndTransport = mapPlansAndTransport(plans, transport)
            )

    )
    MyTripScreen(viewModel = viewModel)
}