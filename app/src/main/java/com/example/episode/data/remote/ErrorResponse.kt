package com.example.episode.data.remote

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name = "message") val message: String,
    @Json(name = "errorType") val errorType: Int,
)