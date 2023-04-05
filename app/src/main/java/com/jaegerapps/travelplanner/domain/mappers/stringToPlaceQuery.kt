package com.jaegerapps.travelplanner.domain.mappers

import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo
import java.net.URLEncoder

fun latLngToLocationString(location: PlaceLocationInfo): String {

    val url = "${URLEncoder.encode(location.lat.toString(), "UTF-8")},${URLEncoder.encode(location.long.toString(), "UTF-8")}"
    return url
}
