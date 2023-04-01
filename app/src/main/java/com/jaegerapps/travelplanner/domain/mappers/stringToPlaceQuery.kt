package com.jaegerapps.travelplanner.domain.mappers

import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo
import java.net.URLEncoder
import kotlin.math.pow
import kotlin.math.roundToInt

fun stringToPlaceQuery(location: PlaceLocationInfo): String {

    val latLong = "${location.lat},${location.long}"
    val url = "${URLEncoder.encode(location.lat.toString(), "UTF-8")},${URLEncoder.encode(location.long.toString(), "UTF-8")}"
    return url
}

fun Double.round(numDecimalPlaces: Int): Double {
    val factor = 10.0.pow(numDecimalPlaces)
    return (this * factor).roundToInt() / factor
}