package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class SaveWeatherData @Inject constructor(
    private val repository: OpenWeatherRepository
) {
   suspend operator fun invoke(weatherData: WeatherData, currentTemperature: Temperature,
                        temperature: Temperature,
                        lengthUnit: LengthUnit
    ) = repository.insertFavouriteWeather(weatherData, currentTemperature, temperature, lengthUnit)
}