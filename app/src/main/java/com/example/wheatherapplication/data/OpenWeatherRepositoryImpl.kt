package com.example.wheatherapplication.data

import com.example.wheatherapplication.data.remote.OpenWeatherApiService
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService
) : OpenWeatherRepository {
    override suspend fun getWeather(lat:Double, lng:Double) =
        openWeatherApiService.getOneCall(lat.toString(),lng.toString())


}