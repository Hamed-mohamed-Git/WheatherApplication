package com.example.wheatherapplication.data.remote

import com.example.wheatherapplication.data.dto.OpenWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("onecall")
    suspend fun getOneCall(@Query("lat") lat:String,@Query("lon") lng:String):OpenWeatherDTO
}