package com.example.wheatherapplication.data.repository.fake

import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.data.map.FavouriteWeatherDataMapper
import com.example.wheatherapplication.data.map.WeatherDataMapper
import com.example.wheatherapplication.data.remote.dto.OpenWeatherForeCastDTO
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.mockito.kotlin.doNothing

class FakeOpenWeatherRepository() : OpenWeatherRepository {

    private val weatherDataList: MutableList<WeatherData> = mutableListOf()

    override suspend fun getForeCast(lat: Double, lng: Double): OpenWeatherForeCastDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getFavouriteWeathers(): Flow<List<WeatherData>> = flow {
        emit(weatherDataList)
    }

    override suspend fun insertFavouriteWeather(
        weatherData: WeatherData,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ) {
        weatherDataList.add(
            weatherData
        )

    }

    override suspend fun updateFavouriteWeather(weatherData: WeatherData) {
        weatherDataList.remove(weatherDataList.find { it.lat == weatherData.lat })
        weatherDataList.add(weatherData)
    }

    override suspend fun deleteFavouriteWeather(weatherData: WeatherData) {
        weatherDataList.remove(weatherData)
    }

    override suspend fun getWeather(
        lat: Double,
        lng: Double,
        unit: String,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): WeatherData {
        return WeatherData()
    }

    override suspend fun getFavouriteWeather(
        lat: Double,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): Flow<WeatherData> = flow {
        weatherDataList.find { it.lat == lat }?.let {
            emit(it)
        }
    }

    override suspend fun checkFounded(id: String): Int? {
        weatherDataList.find { it.lat.toString() ==id }?.let {
            return if (weatherDataList.contains(it)) 1 else 0
        }
        return null
    }

    override suspend fun updateCurrentLocationFavouriteWeather(
        id: String,
        latitude: String?,
        longitude: String?,
        weather: String?
    ) {
        doNothing()
    }

    override suspend fun getWeatherById(id: String): Flow<FavouriteWeather> = flow{
        emit(FavouriteWeatherDataMapper.convertToFavouriteWeather(weatherDataList.find { it.lat.toString() == id}))
    }
}