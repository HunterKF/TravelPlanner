package com.jaegerapps.travelplanner.presentation.plan_trip.special_requests

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.SelectableButton

@Composable
fun SpecialRequestsScreen(
    sharedViewModel: SharedViewModel,
    specialRequestsViewModel: SpecialRequestsViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        specialRequestsViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    val state = sharedViewModel.state
    var requestState = specialRequestsViewModel.toggleRequest
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.any_special_requests),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectableButton(
                    text = "Yes",
                    onClick = { requestState.value = true },
                    color = MaterialTheme.colors.primary,
                    isSelected = requestState.value,
                    selectedTextColor = Color.White
                )
                SelectableButton(
                    text = "No",
                    onClick = { requestState.value = false },
                    color = if (!requestState.value) MaterialTheme.colors.primary else Color.LightGray,
                    isSelected = !requestState.value,
                    selectedTextColor = Color.White
                )
            }
            val offsetAnimation: Dp by animateDpAsState(
                if (requestState.value) 0.dp else 10.dp,
                tween()
            )
            if (requestState.value) {

                Spacer(Modifier.height(spacing.spaceMedium))
                SpecialRequestContainer(
                    modifier = Modifier.offset(y = offsetAnimation),
                    specialRequestsViewModel = specialRequestsViewModel,
                    totalDays = sharedViewModel.state.requestItinerary.days.toInt()
                )
            }

        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                sharedViewModel.onStateRequestUpdate(state.requestItinerary.specialRequests)
                specialRequestsViewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}