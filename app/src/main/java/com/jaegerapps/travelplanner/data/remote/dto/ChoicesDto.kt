package com.jaegerapps.travelplanner.data.remote.dto

import com.squareup.moshi.Json

data class ChoicesDto(
    val message: MessageDto,
    @field:Json(name = "finish_reason")
    val finish_reason: String,
    @field:Json(name = "index")
    val index: Int
)
