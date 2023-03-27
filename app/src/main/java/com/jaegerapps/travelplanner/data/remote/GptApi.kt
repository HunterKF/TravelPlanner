package com.jaegerapps.travelplanner.data.remote

import com.jaegerapps.travelplanner.BuildConfig
import com.jaegerapps.travelplanner.data.remote.dto.ResponseDto
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GptApi {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.GPT_API_KEY}"
    )
    @POST("chat/completions")
    suspend fun sendSystemSpec(
        @Body prompt: RequestBody,
    ): ResponseDto

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.GPT_API_KEY}"
    )
    @POST("chat/completions")
    suspend fun getResponse(
        @Body prompt: RequestBody,
    ): ResponseDto



    companion object {
        const val BASE_URL = "https://api.openai.com/v1/"
    }
}