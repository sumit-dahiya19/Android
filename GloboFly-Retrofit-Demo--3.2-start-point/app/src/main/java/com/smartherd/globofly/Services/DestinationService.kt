package com.smartherd.globofly.Services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DestinationService {

    @GET()
    fun getDestinationList(): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination( @Path("id") id:Int):Call<Destination>
}