package com.jaegerapps.travelplanner.presentation.singleTrip

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.domain.GptRepository
import com.jaegerapps.travelplanner.domain.models.MealType
import com.jaegerapps.travelplanner.domain.models.RequestItinerary.Companion.toStringRequest
import com.jaegerapps.travelplanner.presentation.SharedViewModel
import com.jaegerapps.travelplanner.presentation.models.PlanTripEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleTripViewModel @Inject constructor(
    private val repository: GptRepository,
) : ViewModel() {

    var state by mutableStateOf(SingleTripState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    val value = mutableStateOf("")

    val meal = MealType(
        meal = "",
        cuisine = ""
    )

    fun onValueChange(newValue: String) {
        value.value = newValue
    }

    fun onEvent(event: PlanTripEvent, context: Context? = null) {
        when (event) {
            is PlanTripEvent.OnLocationChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        location = event.query
                    )
                )
            }
            is PlanTripEvent.OnDurationChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        days = event.query.ifBlank { "0" }
                    )

                )
            }
            is PlanTripEvent.OnInterestsChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        interests = event.query
                    )
                )
            }
            is PlanTripEvent.OnRequestsChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        specialRequests = event.query
                    )

                )
            }
            is PlanTripEvent.OnTransportationChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        transportation = event.query
                    )
                )
            }
            is PlanTripEvent.OnPreferredTransportationChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        preferredTransportation = event.query
                    )
                )
            }
            is PlanTripEvent.OnFindRestaurantChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        findRestaurants = event.query
                    )
                )
            }
            is PlanTripEvent.OnAddMeal -> {
                state =  state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        mealTypes = state.requestItinerary.mealTypes.plus(event.query)
                    )
                )
            }
            is PlanTripEvent.OnMealTypeTimeChange -> {
                meal.copy(
                    meal = event.query

                )

            }
            is PlanTripEvent.OnMealTypeCuisineChange -> {
                meal.copy(
                    cuisine = event.query
                )
            }
            is PlanTripEvent.OnMealTypeFoodRequestChange -> {
                meal.copy(
                    foodRequest = event.query

                )
            }
            is PlanTripEvent.OnClear -> {

            }
            is PlanTripEvent.OnSearch -> {
                if (context != null) {
//                    onSendQuery(context = context, sharedViewModel = sharedViewModel)
                }
            }
        }
    }

    fun onSendQuery(context: Context, sharedViewModel: SharedViewModel) {
        val prompt = state.requestItinerary.toStringRequest(context = context)
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            repository.getResponse(prompt)
                .onSuccess {
                    Log.d("onSendQuery", it.toString())
                    state = state.copy(
                        isLoading = false,
                    )
                    sharedViewModel.onCompletion(it)
                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                }

        }
    }
}