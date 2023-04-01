package com.jaegerapps.travelplanner.domain.models

data class DayPlan(
    val currentDay: String = "",
    val numberOfEvents: Int,
    val planList: List<SinglePlan>,
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
