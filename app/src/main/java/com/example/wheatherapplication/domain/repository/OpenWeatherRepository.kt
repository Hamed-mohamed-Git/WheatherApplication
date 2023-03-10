package com.example.wheatherapplication.domain.repository

import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.data.remote.dto.OpenWeatherOneCallDTO
import com.example.wheatherapplication.data.remote.dto.OpenWeatherForeCastDTO
import com.example.wheatherapplication.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {

    suspend fun getForeCast(lat: Double, lng: Double): OpenWeatherForeCastDTO
    suspend fun getFavouriteWeathers(): Flow<List<WeatherData>>
    suspend fun insertFavouriteWeather(weatherData: WeatherData,currentTemperature: Temperature,
                                       temperature: Temperature,
                                       lengthUnit: LengthUnit)
    suspend fun updateFavouriteWeather(weatherData: WeatherData)
    suspend fun deleteFavouriteWeather(weatherData: WeatherData)

    suspend fun getWeather(
        lat: Double,
        lng: Double,
        unit: String,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): WeatherData

    suspend fun getFavouriteWeather(lat: Double, currentTemperature: Temperature,
                                    temperature: Temperature,
                                    lengthUnit: LengthUnit): Flow<WeatherData>
    suspend fun checkFounded(id: String): Int?
}