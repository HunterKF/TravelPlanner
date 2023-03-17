package com.jaegerapps.travelplanner.data.remote.dto

import com.squareup.moshi.Json

data class MessageDto(
    val role: String,
    val content: String,
)
