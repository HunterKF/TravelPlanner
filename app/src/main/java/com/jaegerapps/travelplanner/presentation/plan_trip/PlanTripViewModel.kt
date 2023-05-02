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
import com.jaegerapps.travelplanner.BuildConfig
import com.jaegerapps.travelplanner.domain.mappers.latLngToLocationString
import com.jaegerapps.travelplanner.domain.models.Itinerary.PlannedItinerary
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toFilterString
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toMultiDayStringRequest
import com.jaegerapps.travelplanner.domain.models.Itinerary.RequestItinerary.Companion.toRequestString
import com.jaegerapps.travelplanner.domain.models.google.PlaceInfo
import com.jaegerapps.travelplanner.domain.models.google.PlaceWrapper
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import com.jaegerapps.travelplanner.domain.repositories.GptRepository
import com.jaegerapps.travelplanner.presentation.models.PlanTripEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PlanTripViewModel @Inject constructor(
    private val gptRepository: GptRepository,
    private val placeRepository: GooglePlaceRepository,
) : ViewModel() {

    var state by mutableStateOf(DayTripState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var allSearchedPlaces = listOf<PlaceInfo>()


    fun onEvent(event: PlanTripEvent, context: Context? = null) {
        when (event) {

            is PlanTripEvent.OnSearch -> {
                if (context != null) {
                    //                    onSendQuery(context = context, sharedViewModel = sharedViewModel)
                }
            }
            is PlanTripEvent.OnMultiSend -> {
                viewModelScope.launch {
                    if (context != null) {
                        onMultiDaySendQuery(context, sharedViewModel = event.sharedViewModel)
                    }
                }
            }
            is PlanTripEvent.OnSingleSend -> {
                if (context != null) {
                    onSendQuery(context, event.sharedViewModel)
                }
            }
            else -> {

            }
        }
    }


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
//        onComplete: (List<PlaceInfo>) -> Unit,
    ): List<PlaceInfo> {
        var nextPage by mutableStateOf("")
        var placeList = listOf<PlaceInfo>()
        val resultPlaces =
            placeRepository.getPlaces(query = query, type = type)
                .onSuccess {
                    nextPage = it.nextPage ?: ""
                    placeList = placeList.plus(it.places)
                    updateAllSearchedPlaces(it)
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                    Log.e("MultiString", "get place error: ${it.message}")
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                }
        // If there is a next page, request it and update the state accordingly
        if (nextPage != "") {
            placeList = placeList.plus(getNextPageAndUpdatePlaces(query, nextPage))
        }
        return placeList
    }

    private fun updateAllSearchedPlaces(it: PlaceWrapper) {
        val filteredList = it.places.filter { place -> place !in allSearchedPlaces }
        Log.d("UpdateAll", "AllSearchedPlaces: $filteredList")

        allSearchedPlaces = allSearchedPlaces.plus(filteredList)
        Log.d("UpdateAll", "AllSearchedPlaces: ${allSearchedPlaces.size}")
    }

    private suspend fun getNextPageAndUpdatePlaces(
        query: String,
        nextPage: String,
    ): List<PlaceInfo> {
        var placeList = listOf<PlaceInfo>()
        val nextPage =
            placeRepository.getNextPage(query, pageToken = nextPage)
                .onSuccess {
                    placeList = placeList.plus(it.places)
                    updateAllSearchedPlaces(it)
                }
                .onFailure {
                    state = state.copy(
                        isLoading = false,
                        error = it.message
                    )
                    _uiEvent.send(UiEvent.ShowSnackbar(UiText.DynamicString("Failed.")))
                }
        return placeList
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
                        temporaryList = getPlaces(
                            query = query,
                            type = it
                        )
                    }.await()
                    var requestCopy = state.requestItinerary
                    requestCopy = requestCopy.copy(
                        places = temporaryList
                    )
                    val prompt = requestCopy.toFilterString(context)
                    if (temporaryList.isNotEmpty()) {
                        val gptResponse = async {
                            getGptFilterResponse(
                                context = context, sharedViewModel = sharedViewModel,
                                prompt = prompt
                            )
                        }.await()
                    } else {
                        state.requestItinerary.interests =
                            state.requestItinerary.interests.plus("food")
                    }
                }
            )
        }

        // Wait for all async operations to complete
        coroutines.awaitAll()

        // Call the getGptResponse function after all coroutines are completed
        getGptResponse(context, sharedViewModel)
    }


    private suspend fun getGptFilterResponse(
        context: Context,
        sharedViewModel: SharedViewModel,
        prompt: String,
    ) {
        val resultGpt =
            gptRepository.filterLocations(prompt)
                .onSuccess { gptFilterPlace ->
                    Log.d("onSendQuery", "on send is starting:$gptFilterPlace")
                    Log.d("onSendQuery", "on send is starting:$allSearchedPlaces")
                    var places = listOf<PlaceInfo>()
                    gptFilterPlace.places.forEach { place ->
                        places = places.plus(
                            PlaceInfo(
                                name = place,
                                photoReference = allSearchedPlaces.firstOrNull() { it.name == place }?.photoReference
                            )
                        )
                    }
                    state = state.copy(
                        isLoading = false,
                        requestItinerary = state.requestItinerary.copy(
                            places = state.requestItinerary.places.plus(
                                places
                            )
                        )
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
                    val updatedPlan = mapPhotoRefToLocation(it)
                    sharedViewModel.onCompletionSingleDay(updatedPlan)
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

    private fun mapPhotoRefToLocation(plannedItinerary: PlannedItinerary): PlannedItinerary {
        plannedItinerary.dayPlan.planList.forEach { plan ->
            plan.photoRef =
                allSearchedPlaces.firstOrNull() { it.name == plan.locationName }?.photoReference
        }
        return addUriToPhotoRef(plannedItinerary)

    }

    private fun addUriToPhotoRef(plannedItinerary: PlannedItinerary): PlannedItinerary {
        plannedItinerary.dayPlan.planList.forEach { plan ->
            if (!plan.photoRef.isNullOrBlank()) {
                val uri = "https://maps.googleapis.com/maps/api/place/photo?key=${BuildConfig.PLACES_API_KEY}&maxwidth=300&photoreference=${plan.photoRef}"

                plan.photoRef = uri
            }
        }
        return plannedItinerary
    }


    private fun getPredictions(address: String) {
        println("Get predictions started")
        viewModelScope.launch {
            println("Get predictions in scope")

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

    suspend fun onMultiDaySendQuery(context: Context, sharedViewModel: SharedViewModel) {
        state = state.copy(
            requestItinerary = sharedViewModel.requestState.requestItinerary
        )
        val days = sharedViewModel.requestState.requestItinerary.days.toInt()

        sharedViewModel._plannedItinerary.value = sharedViewModel._plannedItinerary.value.copy(
            durationOfStay = days.toString()
        )

        state = state.copy(isLoading = true)
        val query = getLocationString()
        (1..days).forEach { i ->
            sharedViewModel.addBlankDayPlan(i)
        }
        (1..days).forEach { i ->
            viewModelScope.async {
                getAndFilterMultiDay(
                    query = query,
                    context = context,
                    sharedViewModel = sharedViewModel,
                    currentDay = i
                )
            }.await()
        }

        // Process the combined result from all days.
    }

    private suspend fun getAndFilterMultiDay(
        query: String,
        context: Context,
        sharedViewModel: SharedViewModel,
        currentDay: Int,
    ) {
        val query = getLocationString()
        Log.d("MultiDay", "getAndFilter has started. Current day: $currentDay")

        // Create a list to store async operations
        val coroutines = mutableListOf<Deferred<Unit>>()



        state.requestItinerary.interests.forEach {
            Log.d("MultiDay", "Running through interests: $it")
            coroutines.add(
                viewModelScope.async {
                    var temporaryList = listOf<PlaceInfo>()
                    fun updateList(places: List<PlaceInfo>) {
                        temporaryList = temporaryList.plus(places)
                    }

                    val places = async {
                        temporaryList = getPlaces(
                            query = query,
                            type = it
                        )
                    }.await()
                    var requestCopy = state.requestItinerary
                    requestCopy = requestCopy.copy(
                        places = temporaryList
                    )
                    val prompt =
                        requestCopy.toMultiDayStringRequest(context, currentDay = currentDay)
                    if (temporaryList.isNotEmpty()) {
                        val gptResponse =
                            withContext(Dispatchers.IO) {
                                getGptFilterMultiResponse(
                                    prompt = prompt,
                                    context = context,
                                    sharedViewModel = sharedViewModel,
                                )
                            }
                    } else {
                        state.requestItinerary.interests =
                            state.requestItinerary.interests.plus("food")
                    }
                }
            )
        }


        // Wait for all async operations to complete
        coroutines.awaitAll()

        // Call the getGptResponse function after all coroutines are completed
        getGptMultiDayResponse(context, sharedViewModel, currentDay)
    }

    private suspend fun getGptMultiDayResponse(
        context: Context,
        sharedViewModel: SharedViewModel,
        currentDay: Int,
    ) {
        val prompt = state.requestItinerary.toMultiDayStringRequest(
            context = context,
            currentDay = currentDay
        )
        val resultGpt =
            gptRepository.getResponse(prompt)
                .onSuccess {
                    Log.d("MultiDay", "on send is starting: $currentDay")
                    Log.d("MultiDay", "Current planned itinerary: $it")
                    var exclusionList = addToExclusion(it, sharedViewModel)
                    Log.d(
                        "onSendQuery",
                        "Exclusion list has been updated: ${sharedViewModel.requestState.requestItinerary.exclusionList} "
                    )
                    Log.d("onSendQuery", "What it was updated with: $exclusionList ")

                    Log.d("onSendQuery", "onSend has now completed ")
                    it.dayPlan.plannedDay = currentDay
                    if (currentDay == 1) {
                        val singleDay = it.dayPlan
                        it.multiDayPlan = it.multiDayPlan.plus(singleDay)
                        sharedViewModel.onCompletionMultiDay(it)
                        _uiEvent.send(UiEvent.Success)
                    } else {
                        sharedViewModel.onAddDayPlan(it.dayPlan)
                    }
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
        if (state.requestItinerary.places.isNotEmpty()) {
        }
    }

    private fun addToExclusion(
        it: PlannedItinerary,
        sharedViewModel: SharedViewModel,
    ): List<String> {
        var exclusionList = listOf<String>()
        it.dayPlan.planList.forEach { singlePlan ->
            exclusionList = exclusionList.plus(singlePlan.locationName)
        }
        state = state.copy(
            isLoading = false,
        )
        sharedViewModel.requestState.requestItinerary =
            sharedViewModel.requestState.requestItinerary.copy(
                exclusionList = exclusionList
            )
        state.requestItinerary = state.requestItinerary.copy(
            exclusionList = state.requestItinerary.exclusionList.plus(exclusionList)
        )
        return exclusionList
    }

    private suspend fun getGptFilterMultiResponse(
        context: Context,
        sharedViewModel: SharedViewModel,
        prompt: String,
    ) {
        val resultGpt =
            gptRepository.filterLocations(prompt)
                .onSuccess { gptFilterPlace ->
                    Log.d("onSendQuery", "on send is starting:$gptFilterPlace")
                    val alreadyFilteredPlaces = state.requestItinerary.places.map { it.name }

                    var places = listOf<PlaceInfo>()
                    gptFilterPlace.places.forEach { place ->
                        if (!alreadyFilteredPlaces.contains(place) && place.isNotBlank())
                            places = places.plus(
                                PlaceInfo(
                                    name = place,
                                    photoReference = allSearchedPlaces.first { it.name == place }.photoReference
                                        ?: ""
                                )
                            )
                    }
                    state = state.copy(
                        isLoading = false,
                        requestItinerary = state.requestItinerary.copy(
                            places = state.requestItinerary.places.plus(
                                places
                            )
                        )
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

