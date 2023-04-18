package com.jaegerapps.travelplanner.presentation.plan_trip.interests

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.presentation.models.PlanTripEvent
import com.jaegerapps.travelplanner.presentation.plan_trip.PlanTripViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.about_trip.InterestsViewModel
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.Interest
import com.jaegerapps.travelplanner.presentation.ui_components.SelectableButton
import com.jaegerapps.travelplanner.presentation.ui_components.StringTextField

@Composable
fun InterestsScreen(
    sharedViewModel: SharedViewModel,
    locationViewModel: InterestsViewModel = hiltViewModel(),
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

    when (state.isLoading) {
        true -> {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize()
            )
        }
        false -> {
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
                        text = stringResource(id = R.string.what_are_your_interests),
                        style = MaterialTheme.typography.h3
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
                        horizontalArrangement = Arrangement.spacedBy(spacing.spaceSmall)
                    ) {
                        items(Interest.interests) {
                            var isSelected by remember {
                                mutableStateOf(
                                    false
                                )
                            }

                            SelectableButton(
                                modifier = Modifier.fillMaxHeight(),
                                text = it.value,
                                isSelected = isSelected,
                                color = MaterialTheme.colors.primary,
                                selectedTextColor = Color.White,
                                onClick = {
                                    if (sharedViewModel.requestState.requestItinerary.interests.size < 3 && !isSelected) {
                                        isSelected = if (!isSelected) {
                                            sharedViewModel.onInterestAdd(it.value)
                                            true
                                        } else {
                                            sharedViewModel.onInterestRemove(it.value)
                                            false
                                        }
                                    } else if (isSelected) {
                                        isSelected = false
                                        sharedViewModel.onInterestRemove(it.value)

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Can only select 3 interests.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })
                        }
                    }
                    /*StringTextField(value = state.requestItinerary.interests, onValueChange = {
                        sharedViewModel.onInterestsChange(it)
                    })*/

                }

                ActionButton(
                    text = stringResource(id = R.string.next),
                    isEnabled = true,
                    onClick = {
//                sharedViewModel.onInterestsChange(state.requestItinerary.interests)
                        if (state.requestItinerary.multiDay) {
                            planTripViewModel.onEvent(
                                PlanTripEvent.OnMultiSend(sharedViewModel = sharedViewModel),
                                context = context

                            )
                        } else {
                            planTripViewModel.onEvent(
                                PlanTripEvent.OnSingleSend(
                                    sharedViewModel
                                ),
                                context = context
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }

}

@Preview
@Composable
fun InterestPreview() {
    val sharedViewModel = SharedViewModel()
    InterestsScreen(sharedViewModel = sharedViewModel) {
        /*TODO*/
    }
}