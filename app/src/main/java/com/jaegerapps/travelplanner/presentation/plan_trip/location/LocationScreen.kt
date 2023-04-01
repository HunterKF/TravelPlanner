package com.jaegerapps.travelplanner.presentation.plan_trip.location

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.jaegerapps.travelplanner.presentation.models.LocalLocation
import com.jaegerapps.travelplanner.presentation.plan_trip.PlanTripViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.StringTextField

@Composable
fun LocationScreen(
    sharedViewModel: SharedViewModel,
    locationViewModel: LocationViewModel = hiltViewModel(),
    planTripViewModel: PlanTripViewModel = hiltViewModel(),
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
            /*StringTextField(value = state.requestItinerary.location, onValueChange = {
                planTripViewModel.onSearchAddressChange(it)
                sharedViewModel.onLocationChange(it)
            })*/
            TextFieldWithDropdownUsage(
                dataIn = planTripViewModel.state.predictions.predictions,
                label = "cities",
                sharedViewModel = sharedViewModel,
                planTripViewModel = planTripViewModel
            )
            /*if (planTripViewModel.state.predictions.predictions.isNotEmpty()) {
                LazyColumn {
                    items(planTripViewModel.state.predictions.predictions) { place ->
                        Text(
                            text = place.name
                        )
                    }
                }
            }*/

        }
        ActionButton(
            text = stringResource(id = R.string.next),
            isEnabled = sharedViewModel.localLocation.placeId.isNotBlank(),
            onClick = {
                planTripViewModel.onLocationNext(sharedViewModel)
                sharedViewModel.onLocationChange(state.requestItinerary.location.location)
                locationViewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun TextFieldWithDropdownUsage(
    dataIn: List<GooglePredictionTerm>,
    label: String = "",
    take: Int = 3,
    selectedLocation: GooglePredictionTerm = GooglePredictionTerm(),
    sharedViewModel: SharedViewModel,
    planTripViewModel: PlanTripViewModel,
) {

    val dropDownExpanded = remember { mutableStateOf(false) }
    val state = sharedViewModel.requestState
    fun onDropdownDismissRequest() {
        dropDownExpanded.value = false
    }

    fun onValueChanged(value: String) {
        dropDownExpanded.value = true
        planTripViewModel.onSearchAddressChange(value)
        sharedViewModel.onLocationChange(value)
       /* if (state.requestItinerary.location.location == planTripViewModel.state.predictions.predictions.first().name) {
            sharedViewModel.onDropDownClick(
                LocalLocation(
                    name = value,
                    placeId = planTripViewModel.state.predictions.predictions.first().placeId
                )
            )
        }*/
    }

    fun onDropDownClick(location: LocalLocation) {
        sharedViewModel.onDropDownClick(location)
        dropDownExpanded.value = false
    }

    TextFieldWithDropdown(
        modifier = Modifier.fillMaxWidth(),
        value = state.requestItinerary.location.location,
        onDismissRequest = ::onDropdownDismissRequest,
        dropDownExpanded = dropDownExpanded.value,
        list = planTripViewModel.state.predictions.predictions,
        onValueChange = ::onValueChanged,
        onDropDownClick = ::onDropDownClick
    )
}

@Composable
fun TextFieldWithDropdown(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    dropDownExpanded: Boolean,
    list: List<GooglePredictionTerm>,
    onDropDownClick: (LocalLocation) -> Unit,
) {
    Box(modifier) {
        StringTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused)
                        onDismissRequest()
                },
            value = value,
            onValueChange = { newValue -> onValueChange(newValue) }
        )
        DropdownMenu(
            expanded = dropDownExpanded,
            properties = PopupProperties(
                focusable = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ) {
            list.forEach { text ->
                DropdownMenuItem(onClick = {
                    onDropDownClick(
                        LocalLocation(
                            name = text.name,
                            placeId = text.placeId
                        )
                    )
//                    selectedLocation = text
                }) {
                    Text(text = text.name)
                }
            }
        }
    }
}