package com.jaegerapps.travelplanner.data.models.google_places

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jaegerapps.travelplanner.data.remote.valid_location_info_response
import com.google.common.truth.Truth.assertThat


import org.junit.Before
import org.junit.Test

class LocationInfoDtoTest {

    private lateinit var expectedResult: LocationDto
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder().setLenient().create()
        expectedResult = LocationDto(
            lat = 28.4652554,
            lng = 77.5109279
        )
    }

    @Test
    fun `JSON to LocationInfoDto`() {
        val location = gson.fromJson(valid_location_info_response, LocationResultWrapperDto::class.java)
        println(location)
        assertThat(location.result.geometry.location.lat).isEqualTo(expectedResult.lat)
        assertThat(location.result.geometry.location.lng).isEqualTo(expectedResult.lng)
    }
}