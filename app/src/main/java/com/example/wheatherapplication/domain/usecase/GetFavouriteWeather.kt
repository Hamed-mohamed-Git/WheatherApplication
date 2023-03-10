package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject


class GetFavouriteWeather @Inject constructor(
    private val repository: OpenWeatherRepository
) {

    suspend operator fun invoke(lat: Double, currentTemperature: Temperature,
                                temperature: Temperature,
                                lengthUnit: LengthUnit
    ) =
        repository.getFavouriteWeather(lat, currentTemperature, temperature, lengthUnit)
}