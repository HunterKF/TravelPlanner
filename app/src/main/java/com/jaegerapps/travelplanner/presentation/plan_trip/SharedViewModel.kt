package com.jaegerapps.travelplanner.presentation.plan_trip

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.*
import com.jaegerapps.travelplanner.presentation.models.LocalLocation
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
            )

        )
    )
    var _plannedItinerary = plannedItinerary

    //this is what we change to make the request
    var requestState by mutableStateOf(DayTripState())
        private set

    var localLocation by mutableStateOf(LocalLocation())

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
            )

        )
    )
        private set

    val currentDay = mutableStateOf(1)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


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
                location =  requestState.requestItinerary.location.copy(
                    location = value
                )
            )
        )
    }

    fun onDropDownClick(value: LocalLocation) {
        requestState.requestItinerary.location = requestState.requestItinerary.location.copy(
            location = value.name
        )
        localLocation = LocalLocation(
            name = value.name,
            placeId = value.placeId
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
}