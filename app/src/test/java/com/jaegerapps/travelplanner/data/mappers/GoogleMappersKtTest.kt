package com.jaegerapps.travelplanner.data.mappers

import com.jaegerapps.travelplanner.data.models.google_places.AutoCompleteDto
import com.jaegerapps.travelplanner.data.models.google_places.PredictionPlaceDto
import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.google.common.truth.Truth.assertThat
import com.jaegerapps.travelplanner.data.models.google_places.PlaceDto
import com.jaegerapps.travelplanner.data.models.google_places.PlaceResultDto
import com.jaegerapps.travelplanner.domain.models.google.PlaceInfo
import com.jaegerapps.travelplanner.domain.models.google.PlaceWrapper
import org.junit.Before
import org.junit.Test

class GoogleMappersKtTest {

    private lateinit var autoCompleteDto: AutoCompleteDto
    private lateinit var placeResultDto: PlaceResultDto
    private lateinit var expectedPredictionResult: GooglePrediction
    private lateinit var expectedWrapperResult: PlaceWrapper

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
        expectedPredictionResult = GooglePrediction(
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
        expectedWrapperResult = PlaceWrapper(
            nextPage = "1234",
            places = listOf(
                PlaceInfo(
                    name = "Hunter's House",
                    /*type = listOf(
                        "establishment",
                        "point_of_interest"
                    )*/
                ),
                PlaceInfo(
                    name = "Krez's House",
                    /*type = listOf(
                        "establishment",
                        "point_of_interest"
                    )*/
                ),
                PlaceInfo(
                    name = "Alex's House",
                    /*type = listOf(
                        "establishment",
                        "point_of_interest"
                    )*/
                )
            )
        )
        placeResultDto = PlaceResultDto(
            nextPage = "1234",
            places = listOf(
                PlaceDto(
                    name = "Hunter's House",
                    types = listOf(
                        "establishment",
                        "point_of_interest"
                    ),
                    address = "1234 Hunter"
                ),
                PlaceDto(
                    name = "Krez's House",
                    types = listOf(
                        "establishment",
                        "point_of_interest"
                    ),
                    address = "1234 Krez"

                ),
                PlaceDto(
                    name = "Alex's House",
                    types = listOf(
                        "establishment",
                        "point_of_interest"
                    ),
                    address = "1234 Alex"

                )
            )
        )
    }

    @Test
    fun `AutoCompleteDto to GooglePrediction`() {
        val googlePrediction = autoCompleteDto.toGooglePrediction()
        assertThat(googlePrediction.predictions.size).isEqualTo(expectedPredictionResult.predictions.size)
    }
    @Test
    fun `PlaceDto to GoogleWrapper`() {
        val googleResult = placeResultDto.toPlaceWrapper()
        assertThat(googleResult.nextPage).isEqualTo(expectedWrapperResult.nextPage)
        assertThat(googleResult.places.size).isEqualTo(expectedWrapperResult.places.size)
    }
}