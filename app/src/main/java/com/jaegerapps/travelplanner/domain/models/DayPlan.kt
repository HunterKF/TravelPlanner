package com.jaegerapps.travelplanner.domain.models

data class DayPlan(
    val currentDay: Int,
    val numberOfEvents: Int,
    val planList: List<SinglePlan>,
    val transportationDetails: List<TransportationDetails?>,

    )
