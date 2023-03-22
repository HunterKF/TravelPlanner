package com.jaegerapps.travelplanner.presentation

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
import com.jaegerapps.travelplanner.domain.models.MealTime
import com.jaegerapps.travelplanner.domain.models.MealRequest
import com.jaegerapps.travelplanner.domain.models.PreferredTransport
import com.jaegerapps.travelplanner.domain.models.RequestItinerary.Companion.toStringRequest
import com.jaegerapps.travelplanner.domain.models.SpecialRequest
import com.jaegerapps.travelplanner.presentation.models.PlanTripEvent
import com.jaegerapps.travelplanner.presentation.plan_trip.SharedViewModel
import com.jaegerapps.travelplanner.presentation.plan_trip.SingleTripState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanTripViewModel @Inject constructor(
    private val repository: GptRepository,
) : ViewModel() {

    var state by mutableStateOf(SingleTripState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    val meal = MealRequest(
        meal = MealTime.Dinner,
        cuisine = "",
        id = 1
    )

    private val requests = mutableListOf<SpecialRequest>(
        SpecialRequest(
            id = 1,
            day = 1,
            request = ""
        )
    )




    fun onEvent(event: PlanTripEvent, context: Context? = null) {
        when (event) {
            is PlanTripEvent.OnLocationChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        location = event.query
                    )
                )
            }
            is PlanTripEvent.OnAboutTripChange -> {
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        aboutTrip = event.query
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
                requests.add(event.query)
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        specialRequests = requests
                    )

                )
            }
            is PlanTripEvent.OnRequestDelete -> {
                requests.minus(event.query)
                state = state.copy(
                    requestItinerary = state.requestItinerary.copy(
                        specialRequests = requests
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
                        preferredTransportation = PreferredTransport.fromString(event.query)
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
                        mealRequests = state.requestItinerary.mealRequests.plus(event.query)
                    )
                )
            }
            is PlanTripEvent.OnMealTypeTimeChange -> {
                meal.copy(
                    meal = MealTime.fromString(event.query)

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