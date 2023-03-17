package com.jaegerapps.travelplanner.data.mappers

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.jaegerapps.travelplanner.data.models.itineraryDTO.DayPlanDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.ItineraryDto
import com.jaegerapps.travelplanner.data.models.itineraryDTO.PlanDto
import com.jaegerapps.travelplanner.data.remote.dto.ChoicesDto
import com.jaegerapps.travelplanner.data.remote.dto.MessageDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import org.junit.Before

import org.junit.Test

class ResponseMapperKtTest {

    private lateinit var gson: Gson
    private lateinit var messageDto: MessageDto
    private lateinit var itineraryDto: ItineraryDto
    private lateinit var responseDto: ResponseDto

    @Before
    fun setUp() {
        gson = Gson()
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
                    day = 1,
                    events = 2,
                    plans = listOf(
                        PlanDto(
                            address = "5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan",
                            name = "Tsukiji Outer Market",
                            description = "A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.",
                            keywords = "local food,shopping,market",
                            type = "sightseeing"
                        ),
                        PlanDto(
                            address = "1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan",
                            name = "Omotesando",
                            description = "A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.",
                            keywords = "shopping,food,fashion brands,architecture",
                            type = "sightseeing"
                        ),
                    ),
                    transportation = listOf(null)
                )

            )
        )
        responseDto = ResponseDto(
            id = "123",
            choices = arrayOf(
                ChoicesDto(
                    message = MessageDto(
                        role = "assistant",
                        content = "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Local food and shopping\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan\",\"name\":\"Tsukiji Outer Market\",\"description\":\"A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.\",\"keywords\":\"local food,shopping,market\",\"type\":\"sightseeing\"},{\"address\":\"1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan\",\"name\":\"Omotesando\",\"description\":\"A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.\",\"keywords\":\"shopping,food,fashion brands,architecture\",\"type\":\"sightseeing\"}],\"transportation\":[]}}}"
                    ),
                    finish_reason = "stop",
                    index = 0
                )
            )
        )
    }

    @Test
    fun toResponseInfo() {

        val result = responseDto.toResponseInfo()

        assertThat(result.itinerary.itinerary.interests).isEqualTo(itineraryDto.interests)

    }
    @Test
    fun toItinerary() {
        val result = responseDto.toResponseInfo().toItinerary()
        assertThat(result.interests).isEqualTo(itineraryDto.interests)
    }

    @Test
    fun toJsonObject() {
        /*TODO*/
    }
}