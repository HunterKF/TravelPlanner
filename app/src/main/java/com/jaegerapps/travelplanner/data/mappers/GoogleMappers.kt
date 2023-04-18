package com.jaegerapps.travelplanner.data.mappers

import com.jaegerapps.travelplanner.data.models.google_places.AutoCompleteDto
import com.jaegerapps.travelplanner.data.models.google_places.LocationResultWrapperDto
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.domain.models.google.*

fun PlaceResultDto.toPlaceWrapper(): PlaceWrapper {
    println("PlaceResultDto: $this")

    var newPlaces = listOf<PlaceInfo>()
    println(this.places)
    this.places.forEach {
        newPlaces = newPlaces.plus(
            PlaceInfo(
                name = it.name,
//                type = it.types
            )
        )
    }
    println(newPlaces)


    return PlaceWrapper(
        nextPage = nextPage,
        places = newPlaces
    )
}

fun AutoCompleteDto.toGooglePrediction(): GooglePrediction {
    var predictionsList = listOf<GooglePredictionTerm>()
    predictions.forEach {
        predictionsList = predictionsList.plus(
            GooglePredictionTerm(
                name = it.description,
                placeId = it.placeId
            )
        )
    }
    return GooglePrediction(predictionsList)
}

fun LocationResultWrapperDto.toPlaceLocationInfo(): PlaceLocationInfo {
    return PlaceLocationInfo(
        lat = result.geometry.location.lat,
        long = result.geometry.location.lng
    )
}