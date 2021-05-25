package com.example.map.ApiData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BitCoinPrice(val price:String?)
