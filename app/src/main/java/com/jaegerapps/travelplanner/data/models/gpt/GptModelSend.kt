package com.jaegerapps.travelplanner.data.models.gpt

data class GptModelSend(
    val model: String = "gpt-3.5-turbo",
    val messages: Array<GptMessageSend>
)
