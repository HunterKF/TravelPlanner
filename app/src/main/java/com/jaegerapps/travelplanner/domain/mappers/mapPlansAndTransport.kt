package com.jaegerapps.travelplanner.domain.mappers

import com.jaegerapps.travelplanner.domain.models.Itinerary.SinglePlan
import com.jaegerapps.travelplanner.domain.models.Itinerary.TransportationDetails

fun mapPlansAndTransport(plans: List<SinglePlan>, transport: List<TransportationDetails>): List<Any> {
            val mappedList = plans.flatMap { singlePlan ->
                val matchingTransportationDetails = transport.filter { transportationDetail ->
                    transportationDetail.startingAddress == singlePlan.address
                }
                listOf(singlePlan) + matchingTransportationDetails
            }
            return mappedList
        }