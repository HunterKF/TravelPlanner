package com.jaegerapps.travelplanner.data.mappers

import com.jaegerapps.travelplanner.data.models.google_places.AutoCompleteDto
import com.jaegerapps.travelplanner.data.models.google_places.PredictionPlaceDto
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GoogleMappersKtTest {

    private lateinit var autoCompleteDto: AutoCompleteDto
    private lateinit var expectedResult: GooglePrediction

    @Before
    fun setUp() {
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
        expectedResult = GooglePrediction(
            predictions = listOf(
                GooglePredictionTerm(
                    name = "Paris, France",
                    placeId = "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg"
                ),
                GooglePredictionTerm(
                    name = "Pari Chowk, NRI City, Omega II, Noida, Uttar Pradesh, India",
                    placeId = "EjtQYXJpIENob3drLCBOUkkgQ2l0eSwgT21lZ2EgSUksIE5vaWRhLCBVdHRhciBQcmFkZXNoLCBJbmRpYSIuKiwKFAoSCSvbxs7VwQw5EaQKqM6g9V1WEhQKEgnPC8bw08EMORFiiyvvNAwEXg"
                ),
                GooglePredictionTerm(
                    name = "Paris, TX, USA",
                    placeId = "ChIJmysnFgZYSoYRSfPTL2YJuck"
                ),
                GooglePredictionTerm(
                    name = "Parigi, Province of Parma, Italy",
                    placeId = "ChIJlfM5m4FygEcRZGwT9ziKmtE"
                ),
                GooglePredictionTerm(
                    name = "Paris, Brant, ON, Canada",
                    placeId = "ChIJsamfQbVtLIgR-X18G75Hyi0"
                )
            )
        )
    }

    @Test
    fun `AutoCompleteDto to GooglePrediction`() {
        val googlePrediction = autoCompleteDto.toGooglePrediction()
        assertThat(googlePrediction.predictions.size).isEqualTo(expectedResult.predictions.size)
    }
}