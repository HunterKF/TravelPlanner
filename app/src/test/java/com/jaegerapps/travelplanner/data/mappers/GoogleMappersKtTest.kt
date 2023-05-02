package com.jaegerapps.travelplanner.data.mappers

import com.jaegerapps.travelplanner.domain.models.google.GooglePrediction
import com.jaegerapps.travelplanner.domain.models.google.GooglePredictionTerm
import com.google.common.truth.Truth.assertThat
import com.jaegerapps.travelplanner.data.models.google_places.*
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
                    photoReference = "AUjq9jnSAOrZ_m0TMxLsspvws0Gw4hv6Rd9x-qrFQSKj45iC47xJOfrtTwCfoH7fLJtLXKCUctGh1MMPBxsGVSJR2tAxpWR1kdnKYIfKprins4ewwTgkXj_DDbBIRxjn8hpPBtU11kK6uIOTQxWyLCxQbkKq9W1ZXp7W2iW3xTR4ecJbqTM9"
                ),
                PlaceInfo(
                    name = "Krez's House",
                    /*type = listOf(
                        "establishment",
                        "point_of_interest"
                    )*/
                    photoReference = "AUjq9jnOy1gLHSraw02_PYsiBFrIeElwEr_CREi4O73Rd2SpP-qwtiIjDYGddUCxW6CuL0vz4LZmNeyL8fHzbevHFE32sfAUujyRYPZfdv1-Hmy3ULHxxqh0dziQ-u6QsFhDPsJ-s-bgAKMpVrs_x0dBfK5VSzVrGF3mo3e0f88iUKM-E-Sy"
                ),
                PlaceInfo(
                    name = "Alex's House",
                    /*type = listOf(
                        "establishment",
                        "point_of_interest"
                    )*/
                    photoReference = "AUjq9jnxoKhGE7bQUd8XWK1D7Hv8YvwQq_49UTlMUUNSwu5cBtPlJssjwYAsqZ0TpGKhX8MEeO8Hn4z7C5bwG2LsC9LA0OmUOd8LTcSZbhC1F0-8IF7QbAuakr09Vqbm133KO-xPmm0nQNStshLItNeuUaVUSdLt0I5IykIXPAuH3-ZoZ4DC"
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
                    address = "1234 Hunter",
                    photos = arrayOf(
                        PhotoReferenceDto(
                            "AUjq9jnxoKhGE7bQUd8XWK1D7Hv8YvwQq_49UTlMUUNSwu5cBtPlJssjwYAsqZ0TpGKhX8MEeO8Hn4z7C5bwG2LsC9LA0OmUOd8LTcSZbhC1F0-8IF7QbAuakr09Vqbm133KO-xPmm0nQNStshLItNeuUaVUSdLt0I5IykIXPAuH3-ZoZ4DC"
                        )
                    )

                ),
                PlaceDto(
                    name = "Krez's House",
                    types = listOf(
                        "establishment",
                        "point_of_interest"
                    ),
                    address = "1234 Krez",
                    photos = arrayOf(
                        PhotoReferenceDto(

                            "AUjq9jnxoKhGE7bQUd8XWK1D7Hv8YvwQq_49UTlMUUNSwu5cBtPlJssjwYAsqZ0TpGKhX8MEeO8Hn4z7C5bwG2LsC9LA0OmUOd8LTcSZbhC1F0-8IF7QbAuakr09Vqbm133KO-xPmm0nQNStshLItNeuUaVUSdLt0I5IykIXPAuH3-ZoZ4DC"
                        )
                    )

                ),
                PlaceDto(
                    name = "Alex's House",
                    types = listOf(
                        "establishment",
                        "point_of_interest"
                    ),
                    address = "1234 Alex",
                    photos = arrayOf(
                        PhotoReferenceDto(
                            "AUjq9jnxoKhGE7bQUd8XWK1D7Hv8YvwQq_49UTlMUUNSwu5cBtPlJssjwYAsqZ0TpGKhX8MEeO8Hn4z7C5bwG2LsC9LA0OmUOd8LTcSZbhC1F0-8IF7QbAuakr09Vqbm133KO-xPmm0nQNStshLItNeuUaVUSdLt0I5IykIXPAuH3-ZoZ4DC"
                        )
                    )

                )

            ),

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