package com.jaegerapps.travelplanner.data.remote

import com.jaegerapps.travelplanner.BuildConfig
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlaceApi {
    @GET("maps/api/place/textsearch/json?key=${BuildConfig.PLACES_API_KEY}")
    suspend fun getPlaces(
        @Query("query") query: String,
    ): PlaceResultDto
    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}