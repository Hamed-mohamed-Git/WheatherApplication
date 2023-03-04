package com.example.wheatherapplication.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.data.map.WeatherDataMapper
import com.example.wheatherapplication.data.remote.OpenWeatherApiService
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService
) : OpenWeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeather(lat: Double, lng: Double, unit: String) =
        WeatherDataMapper.convertToWeatherData(openWeatherApiService.getOneCall(lat.toString(), lng.toString(),unit))


    override suspend fun getForeCast(lat: Double, lng: Double) =
        openWeatherApiService.getForeCast(lat.toString(), lng.toString())
}