package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.jaegerapps.travelplanner.domain.models.google.GooglePlaceString
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo

interface GooglePlaceRepository {
    suspend fun getPlaces(query: String): Result<GooglePlaceString>

    suspend fun autoComplete(input: String): Result<GooglePrediction>

    suspend fun getPlaceFromId(input: String): Result<PlaceLocationInfo>


}