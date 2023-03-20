package com.jaegerapps.travelplanner.presentation

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.presentation.models.PlanTripEvent
import com.jaegerapps.travelplanner.presentation.singleTrip.SingleTripViewModel

@Composable
fun SingleTripScreen(
    viewModel: SingleTripViewModel = hiltViewModel(),
    onRequestComplete: () -> Unit,
    sharedViewModel: SharedViewModel,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onRequestComplete()
                is UiEvent.ShowSnackbar -> Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT).show()
                else -> Unit
            }
        }
    }
    val state = viewModel.state
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            item {
                TextField(
                    value = state.requestItinerary.location,
                    onValueChange = { newValue -> viewModel.onEvent(PlanTripEvent.OnLocationChange(newValue)) },
                    label = {
                        Text(
                            text = "Location"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                    )
                )
            }
            item {
                TextField(
                    value = state.requestItinerary.days,
                    onValueChange = { newValue -> viewModel.onEvent(PlanTripEvent.OnDurationChange(newValue)) },
                    label = {
                        Text(
                            text = "Duration"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                        }
                    )
                )
            }

            item {
                TextField(
                    value = state.requestItinerary.interests,
                    onValueChange = { newValue -> viewModel.onEvent(PlanTripEvent.OnInterestsChange(newValue)) },
                    label = {
                        Text(
                            text = "Interests"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                        }
                    )
                )
            }

            item {
                TextField(
                    value = state.requestItinerary.specialRequests,
                    onValueChange = { newValue -> viewModel.onEvent(PlanTripEvent.OnRequestsChange(newValue)) },
                    label = {
                        Text(
                            text = "Special Requests"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                        }
                    )
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Find transportation?"
                        )
                        Switch(checked = state.requestItinerary.transportation, onCheckedChange = { viewModel.onEvent(
                            PlanTripEvent.OnTransportationChange(!state.requestItinerary.transportation))})
                    }
                    if (state.requestItinerary.transportation) {

                        TextField(
                            value = state.requestItinerary.preferredTransportation,
                            onValueChange = { newValue ->
                                viewModel.onEvent(
                                    PlanTripEvent.OnPreferredTransportationChange(
                                        newValue
                                    )
                                )
                            },
                            label = {
                                Text(
                                    text = "Preferred Transportation"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                }
                            )
                        )
                    }
                }
            }

            item {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Find restaurants?"
                        )
                        Switch(checked = state.requestItinerary.findRestaurants, onCheckedChange = { viewModel.onEvent(
                            PlanTripEvent.OnFindRestaurantChange(!state.requestItinerary.findRestaurants))})
                    }
                    if (state.requestItinerary.findRestaurants) {
                        TextField(
                            value = state.requestItinerary.preferredTransportation,
                            onValueChange = { newValue ->
                                /*TODO*/
                            },
                            label = {
                                Text(
                                    text = "Meal time?"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                }
                            )
                        )
                        TextField(
                            value = state.requestItinerary.preferredTransportation,
                            onValueChange = { newValue ->
                                /*TODO*/
                            },
                            label = {
                                Text(
                                    text = "Cuisine"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                }
                            )
                        )
                        TextField(
                            value = state.requestItinerary.preferredTransportation,
                            onValueChange = { newValue ->
                                /*TODO*/
                            },
                            label = {
                                Text(
                                    text = "Special requests"
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                imeAction = ImeAction.Search,
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onSend = {
                                }
                            )
                        )
                    }

                }
            }

        }
        Button(onClick = {  viewModel.onSendQuery(context = context, sharedViewModel = sharedViewModel) }) {
            Text(
                text = "Send"
            )
        }
    }
}