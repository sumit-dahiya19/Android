package com.example.map.Network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private const val URL = "https://run.mocky.io/"

    private val okHttp = OkHttpClient.Builder()

    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(servicetype: Class<T>): T {
        return retrofit.create(servicetype)
    }
}