package com.jaegerapps.travelplanner.presentation.plan_trip.transport

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.Dimensions
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.PreferredTransport
import com.jaegerapps.travelplanner.presentation.plan_trip.PlanTripViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.DayTripState
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.SelectableButton

@Composable
fun TransportScreen(
    sharedViewModel: SharedViewModel,
    transportViewModel: TransportViewModel = hiltViewModel(),
    planTripViewModel: PlanTripViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        planTripViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    val state = sharedViewModel.requestState
    val planState = planTripViewModel.state
    var requestTransportState by remember {
        mutableStateOf(false)
    }
    var requestTransportType = transportViewModel.state.value
    when (state.isLoading) {
        true -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Text(
                        text = stringResource(R.string.please_wait),
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Center
                    )
                }


            }
        }
        false -> {
            TransportContent(
                spacing,
                requestTransportState,
                requestTransportType,
                transportViewModel,
                state,
                sharedViewModel,
                planTripViewModel,
                context
            )
        }
    }

}

@Composable
private fun TransportContent(
    spacing: Dimensions,
    requestTransportState: Boolean,
    requestTransportType: PreferredTransport,
    transportViewModel: TransportViewModel,
    state: DayTripState,
    sharedViewModel: SharedViewModel,
    planTripViewModel: PlanTripViewModel,
    context: Context,
) {
    var requestTransportState1 = requestTransportState
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
                text = stringResource(id = R.string.do_you_need_transport),
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectableButton(
                    text = "Yes",
                    onClick = { requestTransportState1 = true },
                    color = if (requestTransportState1) MaterialTheme.colors.primary else Color.LightGray,
                    isSelected = requestTransportState1,
                    selectedTextColor = if (requestTransportState1) Color.White else MaterialTheme.colors.primary
                )
                SelectableButton(
                    text = "No",
                    onClick = { requestTransportState1 = false },
                    color = if (!requestTransportState1) MaterialTheme.colors.primary else Color.LightGray,
                    isSelected = !requestTransportState1,
                    selectedTextColor = if (!requestTransportState1) Color.White else MaterialTheme.colors.primary
                )
            }
            if (requestTransportState1) {
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                TransportationSelector(requestTransportType, transportViewModel, spacing)
            }

        }
        ActionButton(
            text = stringResource(id = R.string.next),
            isEnabled = state.requestItinerary.location.isNotBlank(),
            onClick = {
                sharedViewModel.updateStateTransport(requestTransportType)
                if (state.requestItinerary.multiDay) {
                    planTripViewModel.onMultiDaySendQuery(context = context, sharedViewModel = sharedViewModel)
                } else {
                    planTripViewModel.onSendQuery(context = context, sharedViewModel = sharedViewModel)
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun TransportationSelector(
    requestTransportType: PreferredTransport,
    transportViewModel: TransportViewModel,
    spacing: Dimensions,
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall),

        ) {
        item() {
            SelectableButton(
                text = "walking",
                isSelected = requestTransportType is PreferredTransport.Walking,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.Walking) })

        }
        item {

            SelectableButton(
                text = "buses",
                isSelected = requestTransportType is PreferredTransport.Buses,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.Buses) })
        }
        item {

            SelectableButton(
                text = "metro",
                isSelected = requestTransportType is PreferredTransport.Metro,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.Metro) })
        }
        item {

            SelectableButton(
                text = "public transport",
                isSelected = requestTransportType is PreferredTransport.PublicTransport,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.PublicTransport) })
        }
        item {

            SelectableButton(
                text = "taxi",
                isSelected = requestTransportType is PreferredTransport.Taxi,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.Taxi) })
        }
        item {

            SelectableButton(
                text = "personal car",
                isSelected = requestTransportType is PreferredTransport.PersonalCar,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.PersonalCar) })
        }
        item {

            SelectableButton(
                text = "cycling",
                isSelected = requestTransportType is PreferredTransport.Cycling,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = { transportViewModel.onTransportChange(PreferredTransport.Cycling) })
        }

    }
}

