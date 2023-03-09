package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class DeleteWeatherData @Inject constructor(
    private val repository: OpenWeatherRepository
) {

    suspend operator fun invoke(weatherData: WeatherData) =
        repository.deleteFavouriteWeather(weatherData)
}