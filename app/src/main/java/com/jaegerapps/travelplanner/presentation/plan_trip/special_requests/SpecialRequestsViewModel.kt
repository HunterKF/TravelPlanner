package com.jaegerapps.travelplanner.presentation.plan_trip.special_requests

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.Itinerary.SpecialRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SpecialRequestsViewModel : ViewModel() {
    var state = mutableStateOf(
        listOf(
            SpecialRequest(
                1,
                1,
                ""
            )
        )
    )
        private set

    val toggleRequest = mutableStateOf(false)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onStringChange(id: Int, value: String) {

        state.value.filter { it.id == id }.forEach {
            it.request = value
        }

    }

    fun onDayChange(id: Int, value: Int) {
        state.value.filter { it.id == id }.forEach {
            it.day = value
        }
    }

    fun onRequestAdd() {
        val newId = state.value.size + 1
        state.value = state.value.plus(
            SpecialRequest(
                id = newId,
                day = 1,
                request = ""
            )
        )
    }

    fun onRequestRemove(value: SpecialRequest) {
        println(state.value)

        state.value = state.value.minus(value)
        var newId = 1
        state.value.forEach {
            it.id = newId
            newId++
        }

        println(state.value)
    }

    fun onNextClick() {
        viewModelScope.launch {

            _uiEvent.send(UiEvent.Success)
        }
    }


}