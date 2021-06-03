package com.example.map.ApiData


data class VehicleWS (
    val identifier: String,
    val message: Message
)

data class Message (
    val action: String,
    val payload: Payload
)

data class Payload (
    val latitude: Double,
    val longitude: Double,
    val orientation: Long,
    val speed: Long
)