package com.jaegerapps.travelplanner.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jaegerapps.travelplanner.domain.models.DayPlan
import com.jaegerapps.travelplanner.domain.models.PlannedItinerary

class SharedViewModel: ViewModel() {
    private var plannedItinerary = mutableStateOf( PlannedItinerary(
        "","","", dayPlan = DayPlan(
            "",
            8,
            emptyList(),
            emptyList(),
            listOf(null)
        )
    ))
    var _plannedItinerary = plannedItinerary

    fun onCompletion(incomingItinerary: PlannedItinerary) {
        Log.d("onCompletion", "The function has run.")
        Log.d("onCompletion", incomingItinerary.toString())
        _plannedItinerary.value = incomingItinerary
        Log.d("onCompletion", _plannedItinerary.toString())

    }
}