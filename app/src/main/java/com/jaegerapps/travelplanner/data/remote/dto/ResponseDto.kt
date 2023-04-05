package com.jaegerapps.travelplanner.data.remote.dto

import com.squareup.moshi.Json

data class ResponseDto(
    @field:Json(name = "id")
    val id: String,
    @field:Json(name = "choices")
    val choices: Array<ChoicesDto?>,
)


