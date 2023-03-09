package com.example.wheatherapplication.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherDataDao
import com.example.wheatherapplication.data.map.FavouriteWeatherDataMapper
import com.example.wheatherapplication.data.map.WeatherDataMapper
import com.example.wheatherapplication.data.remote.OpenWeatherApiService
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.usecase.GetGeoCoderLocation
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService,
    private val favouriteWeatherDataDao: FavouriteWeatherDataDao,
    private val getGeoCoderLocation: GetGeoCoderLocation
) : OpenWeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeather(
        lat: Double, lng: Double, unit: String, currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ) =
        WeatherDataMapper.convertToWeatherData(
            openWeatherApiService.getOneCall(
                lat.toString(),
                lng.toString(),
                unit
            ), currentTemperature, temperature, lengthUnit, getGeoCoderLocation(LatLng(lat, lng))
        )

    override suspend fun getForeCast(lat: Double, lng: Double) =
        openWeatherApiService.getForeCast(lat.toString(), lng.toString())


    override suspend fun getFavouriteWeather(id: String) = flow {
        favouriteWeatherDataDao.getFavouriteWeather(id).collect{
            this.emit(FavouriteWeatherDataMapper.convertToWeatherData(it.weather))
        }

    }


    override suspend fun getFavouriteWeathers() = flow {
        favouriteWeatherDataDao.getFavouriteWeathers().collect {
            this.emit(FavouriteWeatherDataMapper.convertToWeatherDataList(it))
        }
    }


    override suspend fun insertFavouriteWeather(weatherData: WeatherData,currentTemperature: Temperature,
                                                temperature: Temperature,
                                                lengthUnit: LengthUnit) =
        favouriteWeatherDataDao.insertFavouriteWeather(
            FavouriteWeatherDataMapper.convertToFavouriteWeather(
                WeatherDataMapper.convertToWeatherData(weatherData,currentTemperature, temperature, lengthUnit)
            )
        )


    override suspend fun updateFavouriteWeather(weatherData: WeatherData) =
        favouriteWeatherDataDao.updateFavouriteWeather(
            FavouriteWeatherDataMapper.convertToFavouriteWeather(
                weatherData
            )
        )


    override suspend fun deleteFavouriteWeather(weatherData: WeatherData) =
        favouriteWeatherDataDao.deleteFavouriteWeather(
            FavouriteWeatherDataMapper.convertToFavouriteWeather(
                weatherData
            )
        )


}