package com.jaegerapps.travelplanner.presentation.models

import com.jaegerapps.travelplanner.domain.models.MealRequest
import com.jaegerapps.travelplanner.domain.models.SpecialRequest

sealed class PlanTripEvent {
    data class OnLocationChange(val query: String): PlanTripEvent()
    data class OnAboutTripChange(val query: String): PlanTripEvent()
    data class OnDurationChange(val query: String): PlanTripEvent()
    data class OnInterestsChange(val query: String): PlanTripEvent()
    data class OnRequestsChange(val query: SpecialRequest): PlanTripEvent()
    data class OnRequestDelete(val query: SpecialRequest): PlanTripEvent()
    data class OnTransportationChange(val query: Boolean): PlanTripEvent()
    data class OnPreferredTransportationChange(val query: String): PlanTripEvent()
    data class OnFindRestaurantChange(val query: Boolean): PlanTripEvent()
    data class OnAddMeal(val query: MealRequest): PlanTripEvent()
    data class OnMealTypeTimeChange(val query: String): PlanTripEvent()
    data class OnMealTypeCuisineChange(val query: String): PlanTripEvent()
    data class OnMealTypeFoodRequestChange(val query: String): PlanTripEvent()
    object OnSearch: PlanTripEvent()
    object OnClear: PlanTripEvent()
}