package com.jaegerapps.travelplanner.presentation.plan_trip

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SharedViewModel : ViewModel() {


    //This state is used to display the MyTrip screen
    private var plannedItinerary = mutableStateOf(
        PlannedItinerary(
            location = "",
            durationOfStay = "",
            interests = "",
            dayPlan =
            DayPlan(
                currentDay = "",
                numberOfEvents = 1,
                planList = listOf(),
                transportationDetails = listOf(),
                planAndTransport = listOf()
            )

        )
    )
    var _plannedItinerary = plannedItinerary

    //this is what we change to make the request
    var requestState by mutableStateOf(DayTripState())
        private set

    var plannedState by mutableStateOf(
        PlannedItinerary(
            location = "",
            durationOfStay = "",
            interests = "",
            dayPlan =
            DayPlan(
                currentDay = "",
                numberOfEvents = 1,
                planList = listOf(),
                transportationDetails = listOf(),
                planAndTransport = listOf()
            )

        )
    )
        private set

    val currentDay = mutableStateOf(1)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    val meal = MealRequest(
        meal = MealTime.Dinner,
        cuisine = "",
        id = 1
    )

    fun onMultiDayClick() {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                multiDay = true
            )
        )
    }

    fun onLocationChange(value: String) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                location = value
            )
        )
    }

    fun onDurationChange(value: String) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                days = value
            )
        )
    }


    fun onCompletion(incomingItinerary: PlannedItinerary) {
        _plannedItinerary.value = incomingItinerary

    }

    fun onAdd(incomingItinerary: PlannedItinerary) {
        _plannedItinerary.value.multiDayPlan =
            _plannedItinerary.value.multiDayPlan.plus(incomingItinerary.dayPlan)
    }

    fun onAboutTripChange(value: String) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                aboutTrip = value
            )
        )
    }

    fun onInterestsChange(value: String) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                interests = value
            )
        )
    }

    fun onStateRequestUpdate(specialRequests: List<SpecialRequest>) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                specialRequests = specialRequests
            )
        )
    }

    fun updateStateMealList(value: List<MealRequest>, newValue: Boolean) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                findRestaurants = newValue,
                mealRequests = value
            )
        )
    }

    fun updateStateTransport(requestTransportType: PreferredTransport) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                transportation = true,
                preferredTransportation = requestTransportType
            )
        )
    }
}