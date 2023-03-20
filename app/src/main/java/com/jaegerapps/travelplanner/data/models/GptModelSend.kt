package com.jaegerapps.travelplanner.data.models

import android.util.Log
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

data class GptModelSend(
    val model: String = "gpt-3.5-turbo",
    val messages: Array<GptMessageSend>
)
