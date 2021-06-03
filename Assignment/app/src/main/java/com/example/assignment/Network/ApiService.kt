package com.example.assignment.Network

import com.example.assignment.VechicleInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("https://run.mocky.io/v3/f55d3019-2997-4153-b029-ed5aeaee8bfa")
    fun getdata(): Call<VechicleInfo>
}