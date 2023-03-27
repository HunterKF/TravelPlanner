package com.jaegerapps.travelplanner.data.repository

import com.jaegerapps.travelplanner.data.mappers.toListOfString
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.data.remote.GooglePlaceApi
import com.jaegerapps.travelplanner.domain.models.GooglePlaceString
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import javax.inject.Inject

class GooglePlaceRepositoryImpl @Inject constructor(
    private val api: GooglePlaceApi
): GooglePlaceRepository {
    override suspend fun getPlaces(query: String): Result<GooglePlaceString> {
        return try {
            Result.success(
               api.getPlaces(query)
                   .toListOfString()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}