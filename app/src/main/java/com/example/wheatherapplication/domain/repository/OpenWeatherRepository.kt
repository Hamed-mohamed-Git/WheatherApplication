package com.example.wheatherapplication.domain.repository

import com.example.wheatherapplication.data.dto.OpenWeatherOneCallDTO
import com.example.wheatherapplication.data.dto.OpenWeatherForeCastDTO
import com.example.wheatherapplication.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface OpenWeatherRepository {
    suspend fun getWeather(lat: Double, lng: Double, unit: String): WeatherData
    suspend fun getForeCast(lat: Double, lng: Double): OpenWeatherForeCastDTO
    suspend fun getFavouriteWeathers(): Flow<List<WeatherData>>
    suspend fun insertFavouriteWeather(weatherData: WeatherData)
    suspend fun updateFavouriteWeather(weatherData: WeatherData)
    suspend fun deleteFavouriteWeather(weatherData: WeatherData)
}