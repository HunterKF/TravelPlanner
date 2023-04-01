package com.jaegerapps.travelplanner.domain.models

sealed class Interests(val type: String) {
    object Couple: Interests("couples")
    object Family: Interests("family")
    object SoloTravel: Interests("solo travel")
    object Nature: Interests("nature")
    object Art: Interests("art")
    object Architecture: Interests("architecture")
    object Attractions: Interests("attractions")
    data class Other(val other: String): Interests(type = other)
    companion object {
        fun fromString(name: String): Interests {
            return when(name.lowercase()) {
                "couples" -> Couple
                "family" -> Family
                "solo travel" -> SoloTravel
                "nature" -> Nature
                "art" -> Art
                "architecture" -> Architecture
                "attractions" -> Attractions
                else -> Other(name)
            }
        }
    }
}
