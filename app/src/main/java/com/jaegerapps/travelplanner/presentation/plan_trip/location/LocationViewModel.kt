package com.jaegerapps.travelplanner.presentation.plan_trip.location

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.presentation.plan_trip.DayTripState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    var selectedActivityLevel by mutableStateOf<String>("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelSelect(location: String) {
        selectedActivityLevel = location
    }

    fun onNextClick() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Success)
        }
    }
    fun updateSharedState(state: DayTripState): DayTripState {
        return state.copy(
            requestItinerary = state.requestItinerary.copy(
                location = selectedActivityLevel
            )
        )
    }

}