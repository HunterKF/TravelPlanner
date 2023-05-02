package com.jaegerapps.travelplanner.presentation.plan_trip.my_trip

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.Itinerary.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.PlanContainer
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.ShowDay
import com.jaegerapps.travelplanner.presentation.plan_trip.my_trip.components.TripTopBar
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton

@Composable
fun MyTripScreen(
    viewModel: SharedViewModel,
) {
    val spacing = LocalSpacing.current
    val state by viewModel.state.collectAsState()
    val currentDayState by viewModel.currentDayState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(spacing.spaceMedium)

    ) {
        TripTopBar(title = state.location)
        Spacer(modifier = Modifier.size(spacing.spaceMedium))
        ShowDay(
            duration = state.durationOfStay,
            currentDay = viewModel.currentDayNumber
        ) { viewModel.setCurrentDay() }
        Spacer(modifier = Modifier.size(spacing.spaceMedium))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            if (state.multiTrip && state.multiDayPlan.isNotEmpty()) {
                when (currentDayState.loaded) {
                    true -> {
                        currentDayState.planList.forEach { item ->
                            item {
                                PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                                Text("No Hello")

                            }
                        }
                    }
                    false -> {
                        item {
                            Box(
                                Modifier
                                    .height(300.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.fillMaxWidth(0.5f)
                                )
                            }
                        }
                    }
                }

            } else {
                state.dayPlan.planList.forEach { item ->
                    item {
                        PlanContainer(plan = item, modifier = Modifier.fillMaxWidth())
                        Text("Hello")
                    }
                }
            }
            item {
                ActionButton(text = "Click me", onClick = {
                    Log.d("MultiDay", "$state")
                })

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
            address = "1234 Hemmingway",
            photoRef = null,
            locationName = "Hunter's House",
            description = "Good place",
            keywords = "dank",
            type = "relaxation",

        ),
        SinglePlan(
            address = "0987 Peter St.",
            photoRef = null,
            locationName = "Krez's House",
            description = "Yummy place",
            keywords = "spicy",
            type = "hot sauce"
        ),
        SinglePlan(
            address = "6785 Haeundae-ro",
            photoRef = null,
            locationName = "Beach Paradise",
            description = "Beach side",
            keywords = "beach",
            type = "beach"
        )
    )
    val day2Plan = listOf(
        SinglePlan(
            "12313",
            photoRef = null,
            "Haeundae",
            "Good place",
            "dank",
            "relaxation"
        ),
        SinglePlan(
            address = "123123123.",
            photoRef = null,
            locationName = "Seoul",
            description = "Yummy place",
            keywords = "spicy",
            type = "hot sauce"
        ),
        SinglePlan(
            address = "asdasdaso",
            photoRef = null,
            locationName = "JANGSANG",
            description = "Beach side",
            keywords = "beach",
            type = "beach"
        )
    )


    viewModel.onCompletionSingleDay(
        PlannedItinerary(
            "Berlin, Germany",
            "3",
            "pop music and beer gardens",
            dayPlan =
            DayPlan(
                plannedDay = 1,
                numberOfEvents = 1,
                planList = day1Plan,
                loaded = true
            ),
            multiTrip = true,
            multiDayPlan = listOf(
                DayPlan(
                    plannedDay = 1,
                    numberOfEvents = 1,
                    planList = day1Plan,
                    loaded = false
                ),
                DayPlan(
                    plannedDay = 2,
                    numberOfEvents = 3,
                    planList = day1Plan,
                    loaded = false
                ),
                DayPlan(
                    plannedDay = 3,
                    numberOfEvents = 3,
                    planList = day2Plan,
                    loaded = false
                ),
                DayPlan(
                    plannedDay = 4,
                    numberOfEvents = 3,
                    planList = day1Plan,
                    loaded = false
                ),
                DayPlan(
                    plannedDay = 5,
                    numberOfEvents = 3,
                    planList = day1Plan,
                    loaded = false
                ),
                DayPlan(
                    plannedDay = 6,
                    numberOfEvents = 3,
                    planList = day1Plan,
                    loaded = false
                ),
            )

        )
    )




    MyTripScreen(viewModel = viewModel)
}