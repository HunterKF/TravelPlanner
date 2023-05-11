package com.jaegerapps.travelplanner.presentation.plan_trip

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.core.util.UiEvent
import com.jaegerapps.travelplanner.domain.models.Itinerary.DayPlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.SpecialRequest
import com.jaegerapps.travelplanner.presentation.models.LocalLocation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SharedViewModel : ViewModel() {


    //This state is used to display the MyTrip screen
    private var plannedItinerary = MutableStateFlow(
        PlannedItinerary(
            location = "",
            durationOfStay = "0",
            interests = "",
            dayPlan =
            DayPlan(
                plannedDay = 1,
                numberOfEvents = 1,
                planList = listOf(),
                loaded = false
            )

        )
    )

    var _plannedItinerary = plannedItinerary

    val state = plannedItinerary.asStateFlow()

    private val currentDay = MutableStateFlow(
        DayPlan(
            plannedDay = 1,
            numberOfEvents = 1,
            planList = listOf(),
            loaded = false
        )
    )

    var _currentDay = currentDay

    val currentDayState = currentDay.asStateFlow()

    //this is what we change to make the request
    var requestState by mutableStateOf(DayTripState())
        private set

    var localLocation by mutableStateOf(LocalLocation())

    var plannedState by mutableStateOf(
        PlannedItinerary(
            location = "",
            durationOfStay = "0",
            interests = "",
            dayPlan =
            DayPlan(
                plannedDay = 1,
                numberOfEvents = 1,
                planList = listOf(),
                loaded = false
            )

        )
    )
        private set

    val currentDayNumber = mutableStateOf(1)
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    //This is used in the add screen, it is a list of places for the user to select from.
    var recommendPlaceList = listOf<SinglePlan>()


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
                location = requestState.requestItinerary.location.copy(
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

    fun addToRecommendList(
        singlePlan: SinglePlan,
    ) {
        recommendPlaceList = recommendPlaceList.plus(singlePlan)
    }

    fun onDurationChange(value: String) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                days = value
            )
        )
        _plannedItinerary.value = _plannedItinerary.value.copy(
            durationOfStay = value
        )
    }


    fun onCompletionSingleDay(incomingItinerary: PlannedItinerary) {
        _plannedItinerary.value = incomingItinerary
    }

    fun onCompletionMultiDay(incomingItinerary: PlannedItinerary) {
        _plannedItinerary.value = _plannedItinerary.value.copy(
            incomingItinerary.location,
            interests = incomingItinerary.interests,
            multiTrip = true,
            multiDayPlan = _plannedItinerary.value.multiDayPlan.map { dayPlan ->
                if (dayPlan.plannedDay == incomingItinerary.dayPlan.plannedDay) {
                    dayPlan.copy(
                        plannedDay = incomingItinerary.dayPlan.plannedDay,
                        numberOfEvents = incomingItinerary.dayPlan.numberOfEvents,
                        planList = incomingItinerary.dayPlan.planList,
                        loaded = true
                    )
                } else {
                    dayPlan
                }
            }
                .sortedByDescending { it.plannedDay }
                .reversed(),
        )
        setCurrentDay()
        Log.d("MultiDay", "onCompletion has been tripped: ${_plannedItinerary.value.multiDayPlan}")
    }

    fun onAddDayPlan(dayPlan: DayPlan) {
        Log.d("MultiDay", "onAdd has been tripped: ${dayPlan}")
        dayPlan.loaded = true
        _plannedItinerary.value = _plannedItinerary.value.copy(
            multiDayPlan = _plannedItinerary.value.multiDayPlan.map {
                if (it.plannedDay == dayPlan.plannedDay) dayPlan else it
            }
        )
        Log.d("MultiDay", "onAdd is completing: ${_plannedItinerary.value.multiDayPlan}")
    }

    fun onInterestAdd(value: String) {
        requestState.requestItinerary.interests =
            requestState.requestItinerary.interests.plus(value)
        println("on add")

        println(requestState.requestItinerary.interests)

    }

    fun onInterestRemove(value: String) {
        requestState.requestItinerary.interests =
            requestState.requestItinerary.interests.minus(value)
        println("on remove")

        println(requestState.requestItinerary.interests)
    }

    fun onAdd(incomingItinerary: PlannedItinerary) {
        Log.d(
            "MultiDay",
            "About to add an itinerary, here is state: ${_plannedItinerary.value.multiDayPlan}"
        )
        _plannedItinerary.value.multiDayPlan =
            _plannedItinerary.value.multiDayPlan.plus(incomingItinerary.dayPlan)
        Log.d(
            "MultiDay",
            "Added an itinerary, here is state: ${_plannedItinerary.value.multiDayPlan}"
        )

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
                interests = requestState.requestItinerary.interests.plus(value)
            )
        )
    }

    fun addBlankDayPlan(currentDay: Int) {
        _plannedItinerary.value.multiDayPlan = _plannedItinerary.value.multiDayPlan
            .plus(
                DayPlan(
                    plannedDay = currentDay,
                    numberOfEvents = 0,
                    planList = listOf(),
                    loaded = false
                )
            )
        Log.d(
            "MultiDay",
            "Blank days have been added completed. ${_plannedItinerary.value.multiDayPlan}"
        )
    }

    fun onStateRequestUpdate(specialRequests: List<SpecialRequest>) {
        requestState = requestState.copy(
            requestItinerary = requestState.requestItinerary.copy(
                specialRequests = specialRequests
            )
        )
    }

    fun onDeleteSinglePlan(plan: SinglePlan) {
        plannedItinerary.value.dayPlan.planList =
            plannedItinerary.value.dayPlan.planList.minus(plan)
        plannedItinerary.value.dayPlan.numberOfEvents = plannedItinerary.value.dayPlan.planList.size
    }

    fun onAddSinglePlan(plan: SinglePlan) {
        plannedItinerary.value.dayPlan.planList =
            plannedItinerary.value.dayPlan.planList.plus(plan)
        plannedItinerary.value.dayPlan.numberOfEvents = plannedItinerary.value.dayPlan.planList.size
    }


    fun setCurrentDay() {
        Log.d("MultiDay", "Current state value: ${state.value}")
        Log.d("MultiDay", "Current _plannedStated value: ${_plannedItinerary.value}")
        Log.d("MultiDay", "Current plannedStated value: ${plannedItinerary.value}")
        val newDay =
            state.value.multiDayPlan.firstOrNull { it.plannedDay == currentDayNumber.value }
        if (newDay != null) {
            Log.d("MultiDay", "set current day: $newDay")
            _currentDay.value = _currentDay.value.copy(
                newDay.plannedDay,
                newDay.numberOfEvents,
                newDay.planList,
                newDay.loaded
            )
        }
    }
}