package com.jaegerapps.travelplanner.domain.repositories

import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.domain.models.GooglePlaceString

interface GooglePlaceRepository {
    suspend fun getPlaces(query: String): Result<GooglePlaceString>
}