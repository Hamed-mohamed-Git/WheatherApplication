package com.example.wheatherapplication.domain.repository

import com.example.wheatherapplication.data.dto.OpenWeatherDTO

interface OpenWeatherRepository {
    suspend fun getWeather(lat: Double, lng: Double): OpenWeatherDTO
}