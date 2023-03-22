package com.jaegerapps.travelplanner.presentation.plan_trip

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.*
import com.jaegerapps.travelplanner.presentation.plan_trip.SingleTripState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SharedViewModel : ViewModel() {
    private var plannedItinerary = mutableStateOf(
        PlannedItinerary(
            "", "", "", dayPlan = DayPlan(
                "",
                8,
                emptyList(),
                emptyList(),
                listOf(null)
            )
        )
    )

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

    fun onLocationChange(value: String) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                location = value
            )
        )

    }

    var _plannedItinerary = plannedItinerary

    fun onCompletion(incomingItinerary: PlannedItinerary) {
        _plannedItinerary.value = incomingItinerary
    }

    fun onAboutTripChange(value: String) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                aboutTrip = value
            )
        )
    }

    fun onInterestsChange(value: String) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                interests = value
            )
        )
    }

    fun onStateRequestUpdate(specialRequests: List<SpecialRequest>) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                specialRequests = specialRequests
            )
        )
    }

    fun updateStateMealList(value: List<MealRequest>) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                mealRequests = value
            )
        )
    }

    fun updateStateTransport(requestTransportType: PreferredTransport) {
        state = state.copy(
            requestItinerary = state.requestItinerary.copy(
                transportation = true,
                preferredTransportation = requestTransportType
            )
        )
    }
}