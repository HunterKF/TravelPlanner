package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.data.mappers.toListOfString
import com.jaegerapps.travelplanner.data.remote.GooglePlaceApi
import com.jaegerapps.travelplanner.data.remote.GptApi
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import okhttp3.Dispatcher
import okhttp3.RequestBody
import javax.inject.Inject

class CallRepositories @Inject constructor(
    private val gptApi: GptApi,
    private val googlePlaceApi: GooglePlaceApi
) {

    fun callGoogle(query: String, prompt: RequestBody) {
        CoroutineScope(IO).launch {
            val resultPlaces = async {
                googlePlaceApi.getPlaces(query).toListOfString()
            }.await()
            val result = async {
                gptApi.getResponse(prompt)
            }.await()
        }

    }
}