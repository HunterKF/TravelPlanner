package com.jaegerapps.travelplanner.domain.models.Itinerary

sealed class PreferredTransport(val preference: String) {
    object Walking: PreferredTransport("walking")
    object Buses: PreferredTransport("buses")
    object Metro: PreferredTransport("metro")
    object PublicTransport: PreferredTransport("public transport")
    object Taxi: PreferredTransport("taxi")
    object PersonalCar: PreferredTransport("personal car")
    object Cycling: PreferredTransport("cycling")
    companion object {
        fun fromString(type: String): PreferredTransport {
            return when(type) {
                "walking" -> Walking
                "buses" -> Buses
                "metro" -> Metro
                "public transport" -> PublicTransport
                "taxi" -> Taxi
                "personal car" -> PersonalCar
                "cycling" -> Cycling
                else -> Walking
            }
        }
    }
}
