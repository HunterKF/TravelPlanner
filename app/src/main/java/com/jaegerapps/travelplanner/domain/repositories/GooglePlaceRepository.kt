package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.domain.models.google.PlaceWrapper
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo

interface GooglePlaceRepository {
    suspend fun getPlaces(query: String, type: String?): Result<PlaceWrapper>
    suspend fun getNextPage(query: String, pageToken: String): Result<PlaceWrapper>

    suspend fun autoComplete(input: String): Result<GooglePrediction>

    suspend fun getPlaceFromId(input: String): Result<PlaceLocationInfo>


}