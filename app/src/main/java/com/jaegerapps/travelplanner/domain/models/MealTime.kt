package com.jaegerapps.travelplanner.domain.models

sealed class MealTime(val type: String) {
    object Breakfast: MealTime("breakfast")
    object Lunch: MealTime("lunch")
    object Dinner: MealTime("dinner")


}
