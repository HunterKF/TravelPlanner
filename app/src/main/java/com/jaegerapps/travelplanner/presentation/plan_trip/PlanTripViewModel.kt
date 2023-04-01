package com.jaegerapps.travelplanner.presentation.plan_trip

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.jaegerapps.travelplanner.domain.mappers.stringToPlaceQuery
import com.jaegerapps.travelplanner.domain.repositories.GptRepository
import com.jaegerapps.travelplanner.domain.models.RequestItinerary.Companion.toMultiDayStringRequest
import com.jaegerapps.travelplanner.domain.models.RequestItinerary.Companion.toStringRequest
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanTripViewModel @Inject constructor(
    private val gptRepository: GptRepository,
    private val placeRepository: GooglePlaceRepository,
) : ViewModel() {

    var state by mutableStateOf(DayTripState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    /* fun onEvent(event: PlanTripEvent, context: Context? = null) {
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
     }*/


    fun onSendQuery(context: Context, sharedViewModel: SharedViewModel) {
        state = state.copy(
            requestItinerary = sharedViewModel.requestState.requestItinerary
        )
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val query =
                stringToPlaceQuery(
                    location = state.requestItinerary.location,
                )

            val resultPlaces = async {
                placeRepository.getPlaces(query)
                    .onSuccess {
                        state = state.copy(
                            requestItinerary = state.requestItinerary.copy(
                                places = it.places
                            )
                        )
                    }
                    .onFailure {
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                    }
            }.await()
            val resultGpt = async {
                val prompt = state.requestItinerary.toStringRequest(context = context)

                gptRepository.getResponse(prompt)
                    .onSuccess {
                        Log.d("onSendQuery", "on send is starting:$it")
                        state = state.copy(
                            isLoading = false,
                        )
                        sharedViewModel.onCompletion(it)
                        Log.d("onSendQuery", "onSend has now completed ")

                        _uiEvent.send(UiEvent.Success)
                    }
                    .onFailure {
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                    }
            }.await()
        }
    }

    fun onSearchAddressChange(address: String) {
        getPredictions(address)
    }

    private fun getPredictions(address: String) {
        viewModelScope.launch {
            state.copy(
                isLoading = true
            )
            val result = async {
                placeRepository.autoComplete(input = address)
                    .onSuccess {
                        Log.d("Predictions", "$it")
                        state = state.copy(
                            isLoading = false,
                            predictions = it
                        )

                    }
                    .onFailure {
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                    }
            }.await()
        }
    }

    fun onMultiDaySendQuery(context: Context, sharedViewModel: SharedViewModel) {
        state = state.copy(
            requestItinerary = sharedViewModel.requestState.requestItinerary
        )
        sharedViewModel.plannedState.durationOfStay =
            sharedViewModel.requestState.requestItinerary.days
        val days = sharedViewModel.requestState.requestItinerary.days.toInt()
        for (i in 1..days) {
            val prompt =
                state.requestItinerary.toMultiDayStringRequest(context = context, currentDay = i)
            viewModelScope.launch {
                state = state.copy(
                    isLoading = true
                )
                gptRepository.getResponse(prompt)
                    .onSuccess {
                        it.dayPlan.planList.forEach { item ->
                            state = state.copy(
                                requestItinerary = state.requestItinerary.copy(
                                    exclusionList = state.requestItinerary.exclusionList.plus(
                                        item.locationName
                                    )
                                )
                            )
                        }
                        if (i == 1) {
                            sharedViewModel.onCompletion(it)
                        } else {
                            sharedViewModel.onAdd(it)
                        }
                        if (i == days) {
                            state = state.copy(
                                isLoading = false,
                            )
                            _uiEvent.send(UiEvent.Success)
                        }
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

    fun onLocationNext(sharedViewModel: SharedViewModel) {
        val placeId = sharedViewModel.localLocation.placeId
        val sharedState = sharedViewModel.requestState.requestItinerary
        viewModelScope.launch {
            state.copy(
                isLoading = true
            )
            val result = async {
                placeRepository.getPlaceFromId(placeId)
                    .onSuccess {
                        Log.d("Predictions", "$it")
                        sharedState.location = sharedState.location.copy(
                            lat = it.lat,
                            long = it.long
                        )
                        println(sharedState.location)

                    }
                    .onFailure {
                        state = state.copy(
                            isLoading = false,
                            error = it.message
                        )
                        _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                    }
            }.await()
        }

    }
}