package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.repository.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherData @Inject constructor(
    private val openWeatherRepositoryImpl: OpenWeatherRepositoryImpl
) {

    suspend operator fun invoke(
        lat: Double,
        lng: Double,
        unit: String,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): Flow<WeatherData> =
        flow {
            this.emit(
                openWeatherRepositoryImpl.getWeather(
                    lat,
                    lng,
                    unit,
                    currentTemperature,
                    temperature,
                    lengthUnit
                )
            )
        }


}