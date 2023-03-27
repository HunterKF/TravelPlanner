package com.jaegerapps.travelplanner.presentation.plan_trip.location

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.StringTextField

@Composable
fun LocationScreen(
    sharedViewModel: SharedViewModel,
    locationViewModel: LocationViewModel = hiltViewModel(),
    onDayTripNext: () -> Unit,
    onMultiTripNext: () -> Unit,
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        locationViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> {
                    if (sharedViewModel.requestState.requestItinerary.multiDay) {
                        onMultiTripNext()
                    } else {
                        onDayTripNext()
                    }
                }
                else -> Unit
            }
        }
    }
    val state = sharedViewModel.requestState
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.where_are_you_going),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            StringTextField(value = state.requestItinerary.location, onValueChange = {
                sharedViewModel.onLocationChange(it)
            })

        }
        ActionButton(
            text = stringResource(id = R.string.next),
            isEnabled = state.requestItinerary.location.isNotBlank(),
            onClick = {
                sharedViewModel.onLocationChange(state.requestItinerary.location)
                locationViewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}