package com.jaegerapps.travelplanner.data.remote

import com.jaegerapps.travelplanner.BuildConfig
import com.jaegerapps.travelplanner.data.models.google_places.AutoCompleteDto
import com.jaegerapps.travelplanner.data.models.google_places.LocationResultWrapperDto
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlaceApi {
    @GET("maps/api/place/nearbysearch/json?keyword=tourist%20attraction&type=tourist%20attraction&key=${BuildConfig.PLACES_API_KEY}")
    suspend fun getPlaces(
        @Query("radius") radius: Int = 3000,
        @Query("location") location: String,
    ): PlaceResultDto

    @GET("maps/api/place/textsearch/json?key=${BuildConfig.PLACES_API_KEY}")
    suspend fun getNextPage(
        @Query("query") query: String,
        @Query("pageToken") pageToken: String,
    ): PlaceResultDto

    @GET("maps/api/place/autocomplete/json?types=geocode&key=${BuildConfig.PLACES_API_KEY}")
    suspend fun autoComplete(
        @Query("input") input: String,
    ): AutoCompleteDto

    @GET("maps/api/place/details/json?fields=geometry/location&key=${BuildConfig.PLACES_API_KEY}")
    suspend fun getPlaceById(
        @Query("place_id") placeId: String,
    ): LocationResultWrapperDto

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }
}