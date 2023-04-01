package com.jaegerapps.travelplanner.data.models.google_places

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jaegerapps.travelplanner.data.remote.valid_autocomplete_response
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class AutoCompleteDtoTest {

    private lateinit var autoCompleteDto: AutoCompleteDto
    private lateinit var gson: Gson
    @Before
    fun setUp() {
        gson = GsonBuilder().setLenient().create()
        autoCompleteDto = AutoCompleteDto(
            predictions = listOf(
                PredictionPlaceDto(
                    description = "Paris, France",
                    placeId = "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg"
                ),
                PredictionPlaceDto(
                    description = "Pari Chowk, NRI City, Omega II, Noida, Uttar Pradesh, India",
                    placeId = "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg"
                ),
                PredictionPlaceDto(
                    description = "Paris, TX, USA",
                    placeId = "ChIJmysnFgZYSoYRSfPTL2YJuck"
                ),
                PredictionPlaceDto(
                    description = "Parigi, Province of Parma, Italy",
                    placeId = "ChIJlfM5m4FygEcRZGwT9ziKmtE"
                ),
                PredictionPlaceDto(
                    description = "Paris, Brant, ON, Canada",
                    placeId = "ChIJsamfQbVtLIgR-X18G75Hyi0"
                )
            )
        )
    }

    @Test
    fun `Return valid autoCompleteDto from JSON`() {
        val json = gson.fromJson(valid_autocomplete_response, AutoCompleteDto::class.java)
        println(json)
        assertThat(json.predictions.size).isEqualTo(autoCompleteDto.predictions.size)
        assertThat(json.predictions[0].description).isEqualTo(autoCompleteDto.predictions[0].description)
    }
}