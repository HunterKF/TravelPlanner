package com.jaegerapps.travelplanner.data.models

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jaegerapps.travelplanner.data.remote.dto.ChoicesDto
import com.jaegerapps.travelplanner.data.remote.dto.MessageDto
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import com.jaegerapps.travelplanner.data.remote.invalidResponse
import com.jaegerapps.travelplanner.data.remote.validResponse
import org.junit.Before
import org.junit.Test

class ResponseDtoTest {
    private lateinit var responseDto: ResponseDto
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder().setLenient().create()
        val messageDto = MessageDto(
            role = "assistant",
            content = "{\"itinerary\":{\"location\":\"Tokyo, Japan\",\"length\":1,\"interests\":\"Local food and shopping\",\"day_plan\":{\"day\":1,\"events\":2,\"plans\":[{\"address\":\"5 Chome-2-1 Tsukiji, Chuo City, Tokyo 104-0045, Japan\",\"name\":\"Tsukiji Outer Market\",\"description\":\"A street market that offers a wide range of fresh seafood, fruits, and vegetables, as well as unique local snacks, ceramics, and cooking utensils.\",\"keywords\":\"local food,shopping,market\",\"type\":\"sightseeing\"},{\"address\":\"1 Chome-8-3 Jingumae, Shibuya City, Tokyo 150-0001, Japan\",\"name\":\"Omotesando\",\"description\":\"A shopping district located in Shibuya that offers a variety of high-end fashion brands, cafes, and restaurants.\",\"keywords\":\"shopping,food,fashion brands,architecture\",\"type\":\"sightseeing\"}],\"transportation\":[]}}}"
        )
        val choicesDto = arrayOf(
            ChoicesDto(
                message = messageDto,
                finish_reason = "stop",
                index = 0
            ),
            null
        )
        responseDto = ResponseDto(
            choices = choicesDto,
            id = "chatcmpl-6ug9wkFQRYOre0jjPWSmpXeTkafny"
        )
    }

    @Test
    fun `Return valid responseDto from JSON`() {
        val json= gson.fromJson(validResponse, ResponseDto::class.java)
        println(json)
        assertThat(json.id).isEqualTo(responseDto.id)
        if (json.choices.isNotEmpty() && responseDto.choices.isNotEmpty()) {
            assertThat(json.choices[0]!!.index).isEqualTo(responseDto.choices[0]!!.index)
            assertThat(json.choices[0]!!.message).isEqualTo(responseDto.choices[0]!!.message)
            assertThat(json.choices[0]!!.finish_reason).isEqualTo(responseDto.choices[0]!!.finish_reason)
        }
    }
    @Test
    fun `Return invalid responseDto from JSON`() {
        val json= gson.fromJson(invalidResponse, ResponseDto::class.java)
        println(json)
        assertThat(json.id).isNotEqualTo(responseDto.id)
        if (json.choices.isNotEmpty() && responseDto.choices.isNotEmpty()) {
            assertThat(json.choices[0]!!.index).isNotEqualTo(responseDto.choices[0]!!.index)
            assertThat(json.choices[0]!!.message).isNotEqualTo(responseDto.choices[0]!!.message)
            assertThat(json.choices[0]!!.finish_reason).isNotEqualTo(responseDto.choices[0]!!.finish_reason)
        }
    }
}