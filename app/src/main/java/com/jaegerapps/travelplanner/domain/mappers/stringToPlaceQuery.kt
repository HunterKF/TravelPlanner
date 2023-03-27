package com.jaegerapps.travelplanner.domain.mappers

import android.content.Context
import com.jaegerapps.travelplanner.R
import java.net.URLEncoder

fun stringToPlaceQuery(location: String, context: Context): String {
    val thingsToDo = context.getString(R.string.things_to_do_in, location)
    val url = URLEncoder.encode(thingsToDo, "utf-8")
    return url
}