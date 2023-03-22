package com.jaegerapps.travelplanner.presentation.plan_trip.find_restaurants

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.R
import com.jaegerapps.travelplanner.core.ui.LocalSpacing
import com.jaegerapps.travelplanner.domain.models.MealRequest
import com.jaegerapps.travelplanner.domain.models.MealTime
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.ui_components.ActionButton
import com.jaegerapps.travelplanner.presentation.ui_components.SelectableButton

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FindRestaurantsScreen(
    sharedViewModel: SharedViewModel,
    findRestaurantsViewModel: FindRestaurantsViewModel = hiltViewModel(),
    onNextClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    LaunchedEffect(key1 = true) {
        findRestaurantsViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
                else -> Unit
            }
        }
    }
    val state = sharedViewModel.state
    val totalDays = sharedViewModel.state.requestItinerary.days.toInt()
    var requestState = findRestaurantsViewModel.toggleRequest
    var mealListState = findRestaurantsViewModel.state
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
                text = stringResource(id = R.string.do_you_want_to_find_restaurants),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SelectableButton(
                    text = "Yes",
                    onClick = { requestState.value = true },
                    color = if (requestState.value) MaterialTheme.colors.primary else Color.LightGray,
                    isSelected = requestState.value,
                    selectedTextColor = if (requestState.value) Color.White else MaterialTheme.colors.primary
                )
                SelectableButton(
                    text = "No",
                    onClick = { requestState.value = false },
                    color = if (!requestState.value) MaterialTheme.colors.primary else Color.LightGray,
                    isSelected = !requestState.value,
                    selectedTextColor = if (!requestState.value) Color.White else MaterialTheme.colors.primary
                )
            }
            if (requestState.value) {
                LazyColumn() {

                    items(mealListState.value) { meal ->
                        var string by remember { mutableStateOf("") }
                        var expandedState by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.White)

                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    ExposedDropdownMenuBox(
                                        modifier = Modifier.width(IntrinsicSize.Max),
                                        expanded = expandedState,
                                        onExpandedChange = {
                                            expandedState = !expandedState
                                        }) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Day ${meal.day}"
                                            )
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown,
                                                contentDescription = null,
                                                modifier = Modifier.alignBy(
                                                    FirstBaseline
                                                )
                                            )
                                            Spacer(Modifier.width(spacing.spaceExtraSmall))
                                            Box(
                                                modifier = Modifier
                                                    .width(1.dp)
                                                    .background(Color.DarkGray)
                                                    .fillMaxHeight()
                                            )
                                        }

                                        ExposedDropdownMenu(
                                            modifier = Modifier.width(80.dp),
                                            expanded = expandedState,
                                            onDismissRequest = { expandedState = false }
                                        ) {
                                            // this is a column scope
                                            // all the items are added vertically
                                            for (i in 1..totalDays) {
                                                DropdownMenuItem(onClick = {
                                                    findRestaurantsViewModel.onDayChange(meal.id, i)
                                                    expandedState = false
                                                }) {
                                                    Text(text = "Day $i")
                                                }
                                            }
                                        }
                                    }

                                    IconButton(onClick = {
                                        findRestaurantsViewModel.onRequestRemove(
                                            meal
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            null
                                        )
                                    }
                                }
                                MealTimeSelector(meal, findRestaurantsViewModel)
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = string,
                                    onValueChange = { newValue ->
                                        string = newValue
                                        findRestaurantsViewModel.onCuisineChange(meal.id, string)
                                    },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.request_cuisine)
                                        )
                                    },
                                    shape = RoundedCornerShape(0.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    maxLines = 3,
                                    textStyle = MaterialTheme.typography.body1,

                                    )
                                TextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = string,
                                    onValueChange = { newValue ->
                                        string = newValue
                                        findRestaurantsViewModel.onCuisineChange(meal.id, string)
                                    },
                                    placeholder = {
                                        Text(
                                            text = stringResource(R.string.any_other_requests)
                                        )
                                    },
                                    shape = RoundedCornerShape(0.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    maxLines = 3,
                                    textStyle = MaterialTheme.typography.body1,

                                    )
                            }

                        }
                        if (meal.id != mealListState.value.size) {
                            Spacer(Modifier.height(spacing.spaceMedium))
                        }
                    }
                }
                Spacer(Modifier.height(spacing.spaceMedium))
                ActionButton(
                    text = "Add more",
                    onClick = { findRestaurantsViewModel.onRequestAdd() })
                if (mealListState.value.size >= totalDays * 3) {
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    Text(
                        text = "Careful Adding too many requests to your trip. The more you add the harder it will be to make the perfect trip.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                sharedViewModel.updateStateMealList(mealListState.value)
                findRestaurantsViewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
private fun MealTimeSelector(
    meal: MealRequest,
    findRestaurantsViewModel: FindRestaurantsViewModel,
) {
    val currentMealState = remember {
        mutableStateOf<MealTime>(MealTime.Lunch)
    }
    val spacing = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(spacing.spaceSmall))
        Text(
            text = stringResource(R.string.which_meal),
            style = MaterialTheme.typography.h6
        )
        Spacer(Modifier.height(spacing.spaceSmall))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SelectableButton(
                text = stringResource(R.string.meal_time_breakfast),
                textStyle = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                ),
                isSelected = currentMealState.value is MealTime.Breakfast,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = {
                    currentMealState.value = MealTime.Breakfast
                    findRestaurantsViewModel.onMealTimeChange(
                        meal.id,
                        MealTime.Breakfast
                    )
                })
            SelectableButton(
                text = stringResource(R.string.meal_time_lunch),
                textStyle = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                ),
                isSelected = currentMealState.value is MealTime.Lunch,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = {
                    currentMealState.value = MealTime.Lunch

                    findRestaurantsViewModel.onMealTimeChange(
                        meal.id,
                        MealTime.Lunch
                    )
                })
            SelectableButton(
                text = stringResource(R.string.meal_time_dinner),
                textStyle = MaterialTheme.typography.button.copy(
                    fontSize = 16.sp,
                ),
                isSelected = currentMealState.value is MealTime.Dinner,
                color = MaterialTheme.colors.primary,
                selectedTextColor = Color.White,
                onClick = {
                    currentMealState.value = MealTime.Dinner

                    findRestaurantsViewModel.onMealTimeChange(
                        meal.id,
                        MealTime.Dinner
                    )
                })
        }
    }
}