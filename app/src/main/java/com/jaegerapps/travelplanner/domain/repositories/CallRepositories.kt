package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.data.mappers.toGooglePlaceString
import com.jaegerapps.travelplanner.data.remote.GooglePlaceApi
import com.jaegerapps.travelplanner.data.remote.GptApi
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.RequestBody
import javax.inject.Inject

class CallRepositories @Inject constructor(
    private val gptApi: GptApi,
    private val googlePlaceApi: GooglePlaceApi
) {

 /*   fun callGoogle(query: String, prompt: RequestBody) {
        CoroutineScope(IO).launch {
            val resultPlaces = async {
                googlePlaceApi.getPlaces(query).toGooglePlaceString()
            }.await()
            val result = async {
                gptApi.getResponse(prompt)
            }.await()
        }

    }*/
}