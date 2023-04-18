package com.jaegerapps.travelplanner.domain.models.Itinerary

data class DayPlan(
    var plannedDay: Int = 0,
    val numberOfEvents: Int,
    val planList: List<SinglePlan>,
    var loaded: Boolean
    ) {
    companion object {
        fun mapPlansAndTransport(plans: List<SinglePlan>, transport: List<TransportationDetails>): List<Any> {
            val mappedList = plans.flatMap { singlePlan ->
                val matchingTransportationDetails = transport.filter { transportationDetail ->
                    transportationDetail.startingAddress == singlePlan.address
                }
                listOf(singlePlan) + matchingTransportationDetails
            }
            return mappedList
        }
    }
}
