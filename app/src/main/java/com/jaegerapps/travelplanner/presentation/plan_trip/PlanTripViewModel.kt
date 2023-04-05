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
import com.jaegerapps.travelplanner.domain.mappers.latLngToLocationString
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toFilterString
import com.jaegerapps.travelplanner.domain.repositories.GptRepository
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toMultiDayStringRequest
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toRequestString
import com.jaegerapps.travelplanner.domain.models.google.PlaceInfo
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
        //here we copy the state so we have an alterable state to use in PlanTripVM
        state = state.copy(
            requestItinerary = sharedViewModel.requestState.requestItinerary
        )
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )
            val query = getLocationString()
            val places = async {
                getAndFilterPlaces(
                    query, context, sharedViewModel
                )
            }.await()
            val response = async { getGptResponse(context, sharedViewModel) }.await()
        }
    }

    fun onSearchAddressChange(address: String) {
        getPredictions(address)
    }


    //this encodes the lat lng to a URL string
    private fun getLocationString(): String {
        return latLngToLocationString(
            location = state.requestItinerary.location,
        )
    }

    private suspend fun getPlaces(
        query: String,
        type: String?,
        onComplete: (List<PlaceInfo>) -> Unit,
    ) {
        var nextPage by mutableStateOf("")
        val resultPlaces =
            placeRepository.getPlaces(query = query, type = type)
                .onSuccess {
                    nextPage = it.nextPage ?: ""
                    onComplete(it.places)
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                }
        // If there is a next page, request it and update the state accordingly
        if (nextPage != "") {
            getNextPageAndUpdatePlaces(query, nextPage)
        }

    }

    private suspend fun getNextPageAndUpdatePlaces(query: String, nextPage: String) {
        val nextPage =
            placeRepository.getNextPage(query, pageToken = nextPage)
                .onSuccess {
                    state = state.copy(
                        requestItinerary = state.requestItinerary.copy(
                            places = state.requestItinerary.places.plus(it.places)
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
    }

    private suspend fun getAndFilterPlaces(
        query: String,
        context: Context,
        sharedViewModel: SharedViewModel,
    ) {
        val query = getLocationString()

        // Create a list to store async operations
        val coroutines = mutableListOf<Deferred<Unit>>()

        state.requestItinerary.interests.forEach {
            coroutines.add(
                viewModelScope.async {
                    var temporaryList = listOf<PlaceInfo>()
                    fun updateList(places: List<PlaceInfo>) {
                        temporaryList = temporaryList.plus(places)
                    }

                    val places = async {
                        getPlaces(
                            query = query,
                            type = it
                        ) {
                            ::updateList
                        }
                    }.await()
                    var requestCopy = state.requestItinerary
                    requestCopy = requestCopy.copy(
                        places = temporaryList
                    )
                    val prompt = requestCopy.toFilterString(context)
                    val gptResponse = async {
                        getGptFilterResponse(
                            context = context, sharedViewModel = sharedViewModel,
                            prompt = prompt
                        )
                    }.await()
                }
            )
        }

        // Wait for all async operations to complete
        coroutines.awaitAll()

        // Call the getGptResponse function after all coroutines are completed
        getGptResponse(context, sharedViewModel)
        /*state.requestItinerary.interests.forEach {
            viewModelScope.launch {
                var temporaryList = listOf<PlaceInfo>()
                fun updateList(places: List<PlaceInfo>) {
                    temporaryList = temporaryList.plus(places)
                }

                val places = async {
                    getPlaces(
                        query = query,
                        type = it
                    ) {
                        ::updateList
                    }
                }.await()
                var requestCopy = state.requestItinerary
                requestCopy = requestCopy.copy(
                    places = temporaryList
                )
                val prompt = requestCopy.toFilterString(context)
                val gptResponse = async {
                    getGptFilterResponse(
                        context = context, sharedViewModel = sharedViewModel,
                        prompt = prompt
                    )
                }.await()

            }
        }*/
    }

    private suspend fun getGptFilterResponse(
        context: Context,
        sharedViewModel: SharedViewModel,
        prompt: String,
    ) {
        val resultGpt =
            gptRepository.filterLocations(prompt)
                .onSuccess {
                    Log.d("onSendQuery", "on send is starting:$it")
                    var places = listOf<PlaceInfo>()
                    it.places.forEach { place ->
                        places = places.plus(
                            PlaceInfo(
                                name = place
                            )
                        )
                    }
                    state = state.copy(
                        isLoading = false,
                        requestItinerary = state.requestItinerary.copy(
                            places = sharedViewModel.requestState.requestItinerary.places.plus(
                                places
                            )
                        )
                    )


                    var newState = state.requestItinerary.copy(
                        places = sharedViewModel.requestState.requestItinerary.places.plus(places)
                    )
                    Log.d("onSendQuery", "onSend has now completed ")

//                    _uiEvent.send(UiEvent.Success)
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("PlanTripVM", it1) }
                }
    }

    private suspend fun getGptResponse(context: Context, sharedViewModel: SharedViewModel) {
        val prompt = state.requestItinerary.toRequestString(context = context)
        val resultGpt =
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
                    it.printStackTrace()
                    it.message?.let { it1 -> Log.e("PlanTripVM", it1) }
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                }
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
                        it.printStackTrace()
                        it.message?.let { it1 -> Log.e("PlanTripVM", it1) }
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
                val result = async {
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
                }.await()
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