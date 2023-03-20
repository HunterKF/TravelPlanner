package com.jaegerapps.travelplanner.presentation.my_trip

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.DayPlan
import com.jaegerapps.travelplanner.domain.models.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.SinglePlan
import com.jaegerapps.travelplanner.domain.models.TransportationDetails
import com.jaegerapps.travelplanner.presentation.SharedViewModel
import com.jaegerapps.travelplanner.presentation.my_trip.components.PlanContainer

@Composable
fun MyTripScreen(
    viewModel: SharedViewModel,
) {
    val spacing = LocalSpacing.current
    LazyColumn(modifier = Modifier.fillMaxWidth(),
    contentPadding = PaddingValues(spacing.spaceLarge) ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Location"
                )
                Text(
                    text = viewModel._plannedItinerary.value.location
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Duration"
                )
                Text(
                    text = viewModel._plannedItinerary.value.durationOfStay
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "interests"
                )
                Text(
                    text = viewModel._plannedItinerary.value.interests
                )
            }
        }
        item {Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "current day"
            )
            Text(
                text = viewModel._plannedItinerary.value.dayPlan.currentDay
            )
        }
        }
        item {Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "number of events"
            )
            Text(
                text = viewModel._plannedItinerary.value.dayPlan.numberOfEvents.toString()
            )
        }
        }
        items(viewModel._plannedItinerary.value.dayPlan.planList) { plan ->
          PlanContainer(plan = plan)
        }
        if (viewModel._plannedItinerary.value.dayPlan.transportationDetails.isNotEmpty()) {
            items(viewModel._plannedItinerary.value.dayPlan.transportationDetails) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "starting address"
                        )
                        Text(
                            text = it!!.startingAddress
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "starting location"
                        )
                        Text(
                            text = it!!.startingLocation
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ending address"
                        )
                        Text(
                            text = it!!.endingAddress
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ending location"
                        )
                        Text(
                            text = it!!.endingLocation
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "directions"
                        )
                        Text(
                            text = it!!.directions
                        )
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
    viewModel._plannedItinerary.value = PlannedItinerary(
        "Berlin, Germany",
        "1",
        "pop music and beer gardens",
        dayPlan = DayPlan(
            currentDay = "1",
            numberOfEvents = 1,
            planList = listOf(
                SinglePlan(
                    address = "Frankfurt",
                    locationName = "Alex's House",
                    description = "It's a cool place that I have never seen once but will see it by the end of this year.",
                    keywords = "cool, dank, hip",
                    type = "chill, beer, hip"
                )
            ),
            transportationDetails = listOf(
                TransportationDetails(
                    startingLocation = "Hunter's House",
                    startingAddress = "Busan, Korea",
                    endingLocation = "Alex's House",
                    endingAddress = "Berlin, Germany",
                    transportationType = "Plane",
                    commuteTime = 1800,
                    directions = "Get on a plane and FLY"
                )
            )
        )
    )
    MyTripScreen(viewModel =viewModel )
}