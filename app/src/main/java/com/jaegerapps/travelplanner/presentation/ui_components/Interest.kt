package com.jaegerapps.travelplanner.presentation.ui_components

sealed class Interest(val value: String) {
    object TouristAttractions: Interest("tourist attraction")
    object Landmarks: Interest("landmarks")
    object Parks: Interest("parks")
    object Beaches: Interest("beaches")
    object Activities: Interest("scenic views")
    object Nightlife: Interest("night life")
    object OutdoorRecreation: Interest("outdoor recreation")
    object Restaurants: Interest("restaurants")
    object Cuisine: Interest("cuisine")
    object StreetFood: Interest("street food")
    companion object {
        val interests = listOf<Interest>(
            TouristAttractions,
            Landmarks,
            Parks,
            Beaches,
            Activities,
            Nightlife,
            OutdoorRecreation,
            Restaurants,
            Cuisine,
            StreetFood
        )
    }
}
