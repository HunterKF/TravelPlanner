package com.jaegerapps.travelplanner.presentation.plan_trip.find_restaurants

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.MealRequest
import com.jaegerapps.travelplanner.domain.models.MealTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FindRestaurantsViewModel : ViewModel() {
    var state = mutableStateOf(
        listOf(
            MealRequest(
                id = 1,
                day = 1,
                meal = MealTime.Dinner,
                cuisine = "",
                foodRequest = ""
            )
        )
    )
        private set

    val toggleRequest = mutableStateOf(false)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onCuisineChange(id: Int, value: String) {
        state.value.filter { it.id == id }.forEach {
            it.cuisine = value
        }
    }
    fun onFoodRequestChange(id: Int, value: String) {
        state.value.filter { it.id == id }.forEach {
            it.foodRequest = value
        }
    }

    fun onDayChange(id: Int, value: Int) {
        state.value.filter { it.id == id }.forEach {
            it.day = value
        }
    }

    fun onMealTimeChange(id: Int, value: MealTime) {
        state.value.filter { it.id == id }.forEach {
            it.meal = value
        }
    }

    fun onRequestAdd() {
        val newId = state.value.size + 1
        state.value = state.value.plus(
            MealRequest(
                id = newId,
                meal = MealTime.Dinner,
                day = 1,
                cuisine = "",
                foodRequest = ""
            )
        )
    }

    fun onRequestRemove(value: MealRequest) {
        state.value = state.value.minus(value)
        var newId = 1
        state.value.forEach {
            it.id = newId
            newId++
        }
    }

    fun onNextClick() {
        viewModelScope.launch {

            _uiEvent.send(UiEvent.Success)
        }
    }

}