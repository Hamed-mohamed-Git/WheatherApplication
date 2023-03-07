package com.example.wheatherapplication.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherData @Inject constructor(
    private val openWeatherRepositoryImpl: OpenWeatherRepositoryImpl
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        internetConnection: Boolean,
        lat: Double,
        lng: Double,
        unit: String,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ):Flow<WeatherData> =
        flow {
            if (internetConnection)
                this.emit(openWeatherRepositoryImpl.getWeather(
                    lat,
                    lng,
                    unit,
                    currentTemperature,
                    temperature,
                    lengthUnit
                ))
            else
                openWeatherRepositoryImpl.getFavouriteWeather("$lat-$lng").collect{
                    this.emit(it)
                }
        }


}