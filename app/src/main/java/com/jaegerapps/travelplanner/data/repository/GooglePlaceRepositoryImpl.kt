package com.jaegerapps.travelplanner.data.repository

import com.jaegerapps.travelplanner.data.mappers.toPlaceWrapper
import com.jaegerapps.travelplanner.data.mappers.toGooglePrediction
import com.jaegerapps.travelplanner.data.mappers.toPlaceLocationInfo
import com.jaegerapps.travelplanner.data.remote.GooglePlaceApi
import com.jaegerapps.travelplanner.domain.models.google.PlaceWrapper
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo
import com.jaegerapps.travelplanner.domain.repositories.GooglePlaceRepository
import javax.inject.Inject

class GooglePlaceRepositoryImpl @Inject constructor(
    private val api: GooglePlaceApi
): GooglePlaceRepository {
    override suspend fun getPlaces(query: String, type: String?): Result<PlaceWrapper> {

        return try {
            Result.success(
               api.getPlaces(location = query, type = type ?: "tourist attractions")
                   .toPlaceWrapper()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getNextPage(query: String, pageToken: String): Result<PlaceWrapper> {
        return try {
            Result.success(
                api.getNextPage(query, pageToken).toPlaceWrapper()
            )

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun autoComplete(input: String): Result<GooglePrediction> {
        return try {
            Result.success(
                api.autoComplete(input).toGooglePrediction()
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPlaceFromId(input: String): Result<PlaceLocationInfo> {
        return try {
            Result.success(
                api.getPlaceById(input).toPlaceLocationInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

}