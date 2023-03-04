package com.example.wheatherapplication.data.remote

import com.example.wheatherapplication.data.dto.OpenWeatherOneCallDTO
import com.example.wheatherapplication.data.dto.OpenWeatherForeCastDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApiService {
    @GET("3.0/onecall")
    suspend fun getOneCall(
        @Query("lat") lat: String,
        @Query("lon") lng: String,
        @Query("units") unit: String
    ): OpenWeatherOneCallDTO

    @GET("2.5/forecast")
    suspend fun getForeCast(
        @Query("lat") lat: String,
        @Query("lon") lng: String
    ): OpenWeatherForeCastDTO
}