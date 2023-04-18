package com.jaegerapps.travelplanner.presentation.plan_trip

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.common.truth.Truth.assertThat
import com.jaegerapps.travelplanner.presentation.models.LocalLocation
import org.junit.Before
import org.junit.Test

class SharedViewModelTest {

    private lateinit var sharedViewModel: SharedViewModel
    @Before
    fun setUp() {
        sharedViewModel = SharedViewModel()
    }

    @Test
    fun onLocationChange() {
        val expectedResult = "12345"

        sharedViewModel.onLocationChange(expectedResult)

        assertThat(sharedViewModel.requestState.requestItinerary.location.location).isEqualTo(expectedResult)

    }

    @Test
    fun onDropDownClick() {
        val expectedResult = LocalLocation(
            name = "Hunter",
            placeId = "12345"
        )
        sharedViewModel.onDropDownClick(expectedResult)
        assertThat(sharedViewModel.localLocation).isEqualTo(expectedResult)
        assertThat(sharedViewModel.requestState.requestItinerary.location.location).isEqualTo(expectedResult.name)
    }

    @Test
    fun onDurationChange() {
    }

    @Test
    fun onCompletion() {
    }

    @Test
    fun onAddDayPlan() {
    }

    @Test
    fun onInterestAdd() {
    }

    @Test
    fun onInterestRemove() {
    }

    @Test
    fun onAdd() {
    }

    @Test
    fun onAboutTripChange() {
    }

    @Test
    fun onInterestsChange() {
    }
}