package com.jaegerapps.travelplanner.domain.mappers

import com.jaegerapps.travelplanner.domain.models.google.PlaceLocationInfo
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class StringToPlaceQueryKtTest {

    private lateinit var location: PlaceLocationInfo
    private lateinit var expectedResult: String
    @Before
    fun setUp() {
        location = PlaceLocationInfo(
            lat = 28.4652554,
            long = 77.5109279
        )
        expectedResult = "28.4652554,77.5109279"
    }

    @Test
    fun `PlaceLocationInfo to encode string`() {
        val string = latLngToLocationString(location)
        println(string)
        assertThat(string).isEqualTo(expectedResult)
    }
}