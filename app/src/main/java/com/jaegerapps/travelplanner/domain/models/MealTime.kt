package com.jaegerapps.travelplanner.domain.models

sealed class MealTime(val type: String) {
    object Breakfast: MealTime("breakfast")
    object Lunch: MealTime("lunch")
    object Dinner: MealTime("dinner")

    companion object {
        fun fromString(type: String): MealTime {
            return when(type) {
                "breakfast" -> Breakfast
                "lunch" -> Lunch
                "dinner" -> Dinner
                else -> Dinner
            }
        }
    }
}
