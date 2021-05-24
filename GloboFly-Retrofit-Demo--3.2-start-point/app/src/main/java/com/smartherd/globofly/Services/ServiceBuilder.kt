package com.smartherd.globofly.Services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private const val URL="http://10.0.2.2:9000/"

    //create a OkHttp client
    private val okHttp=OkHttpClient.Builder()

    //create retrofit builder
    private val builder=Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    private val retrofit = builder.build()

    fun <T> buildService(servicetype:Class<T>):T{
        return retrofit.create(servicetype)
    }


}