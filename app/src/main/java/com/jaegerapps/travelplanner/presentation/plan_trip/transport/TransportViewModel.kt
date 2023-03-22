package com.jaegerapps.travelplanner.presentation.plan_trip.transport

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.PreferredTransport
import com.jaegerapps.travelplanner.domain.models.SpecialRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TransportViewModel : ViewModel() {
    var state = mutableStateOf<PreferredTransport>(
        PreferredTransport.Walking
    )
        private set

    val toggleRequest = mutableStateOf(false)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()



    fun onTransportChange(transport: PreferredTransport) {
        state.value = transport
    }


    fun onNextClick() {
        viewModelScope.launch {

            _uiEvent.send(UiEvent.Success)
        }
    }

}