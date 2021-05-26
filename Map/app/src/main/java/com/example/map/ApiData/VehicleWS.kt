package com.example.map.ApiData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VehicleWS(
    val latitude: Double?,
    val longitude: Double?,
    val orientation: Double?,
    val speed: Double?
)