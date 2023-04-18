package com.jaegerapps.travelplanner.data.mappers

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.gpt.GptFilterPlaceDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.*
import com.jaegerapps.travelplanner.data.remote.dto.ChoicesDto
import com.jaegerapps.travelplanner.data.remote.dto.MessageDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.domain.models.gpt.GptFilterPlace
import org.junit.Before

import org.junit.Test

class ResponseMapperKtTest {

    private lateinit var gson: Gson
    private lateinit var messageDto: MessageDto
    private lateinit var itineraryDto: ItineraryDto
    private lateinit var responseRequestDto: ResponseDto
    private lateinit var responseInfo: ResponseInfoDto
    private lateinit var gptFilterPlaceDto: GptFilterPlaceDto
    private lateinit var expectedGptFilterPlace: GptFilterPlace
    private lateinit var responseFilterDto: ResponseDto

    @Before
    fun setUp() {
        gson = Gson()
        responseInfo = ResponseInfoDto(
            ItineraryWrapperDto(
                ItineraryDto(
                    "Berlin, Germany",
                    1,
                    "local beer gardens and WWII",
                    day_plan = DayPlanDto(
                        "1",
                        events = 2,
                        plans = listOf(
                            PlanDto(
                                name = "Alex's House",
                                description = "A cool house with some cool cats",
                                keywords = "Pig town",
                                type = "family"
                            )
                        ),

                        )
                )
            )
        )
        messageDto = MessageDto(
            role = "assistant",
            content = "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Local food and shopping\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan\",\"name\":\"Tsukiji Outer Market\",\"description\":\"A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.\",\"keywords\":\"local food,shopping,market\",\"type\":\"sightseeing\"},{\"address\":\"1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan\",\"name\":\"Omotesando\",\"description\":\"A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.\",\"keywords\":\"shopping,food,fashion brands,architecture\",\"type\":\"sightseeing\"}],\"transportation\":[]}}}"
        )
        itineraryDto = ItineraryDto(
            location = "Tokyo, Japan",
            length = 1,
            interests = "Local food and shopping",
            day_plan = (
                    DayPlanDto(
                        day = "1",
                        events = 2,
                        plans = listOf(
                            PlanDto(
                                name = "Tsukiji Outer Market",
                                description = "A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.",
                                keywords = "local food,shopping,market",
                                type = "sightseeing"
                            ),
                            PlanDto(
                                name = "Omotesando",
                                description = "A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.",
                                keywords = "shopping,food,fashion brands,architecture",
                                type = "sightseeing"
                            ),
                        ),
                    )

                    )
        )
        responseRequestDto = ResponseDto(
            id = "chatcmpl-6ug9wkFQRYOre0jjPWSmpXeTkafny",
            choices = arrayOf(
                ChoicesDto(
                    message = MessageDto(
                        role = "assistant",
                        content = "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Local food and shopping\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan\",\"name\":\"Tsukiji Outer Market\",\"description\":\"A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.\",\"keywords\":\"local food,shopping,market\",\"type\":\"sightseeing\"},{\"address\":\"1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan\",\"name\":\"Omotesando\",\"description\":\"A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.\",\"keywords\":\"shopping,food,fashion brands,architecture\",\"type\":\"sightseeing\"}],\\\"transportation\\\":[{\\\"starting\\\":\\\"Berlin City Center\\\",\\\"starting_address\\\":\\\"Berlin City Center\\\",\\\"ending\\\":\\\"Cafe-Restaurant am Neuen See\\\",\\\"ending_address\\\":\\\"Am Grossen Wannsee 64, 14109 Berlin, Germany\\\",\\\"type_of_transport\\\":\\\"Walking\\\",\\\"commute_duration\\\":60,\\\"directions\\\":\\\"It takes approximately 60 minutes to walk from the Berlin City Center to Cafe-Restaurant am Neuen See. Head southwest on Unter den Linden towards Brandenburg Gate, then continue straight on Ebertstraße. Turn right onto Scheidemannstraße and turn left onto Tiergartenstraße. Take the left, then keep to the right to stay on John-Foster-Dulles-Allee. Cross the roundabout onto Altonaer Straße and turn left onto Am Großen Wannsee.\\\"}]}}}"
                    ),
                    finish_reason = "stop",
                    index = 0
                )
            )
        )

        responseFilterDto = ResponseDto(
            id = "chatcmpl-6ug9wkFQRYOre0jjPWSmpXeTkafny",
            choices = arrayOf(
                ChoicesDto(
                    message = MessageDto(
                        role = "assistant",
                        content =
                        "{\"places\":[\"Kuta Beach\",\"Seminyak Beach\",\"Nasi Goreng Renon\",\"Jimbaran Beach\",\"Pura Tanah Lot\"]}"
                    ),
                    finish_reason = "stop",
                    index = 0
                )
            )
        )
        expectedGptFilterPlace = GptFilterPlace(
            places = listOf(
                "Kuta Beach",
                "Seminyak Beach",
                "Nasi Goreng Renon",
                "Jimbaran Beach",
                "Pura Tanah Lot",
            )
        )
    }

    @Test
    fun toResponseInfo() {
        val result = responseRequestDto.toResponseInfoDto()
        assertThat(result.itinerary.itinerary.interests).isEqualTo(itineraryDto.interests)

    }

    @Test
    fun toPlannedItinerary() {
        val result = responseRequestDto.toResponseInfoDto().toPlannedItinerary()
        println(result)
        println(itineraryDto)

        assertThat(result.dayPlan.plannedDay).isEqualTo(itineraryDto.day_plan.day)
    }

    @Test
    fun `GptFilterPlaceDto to GptFilterPlace`() {
        val result = responseFilterDto.toGptFilterPlaceDto().toGptFilterPlace()
        println(result)

        assertThat(result).isEqualTo(expectedGptFilterPlace)
    }
}