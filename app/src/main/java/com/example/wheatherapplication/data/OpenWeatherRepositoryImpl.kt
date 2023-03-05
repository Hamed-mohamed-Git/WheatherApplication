package com.example.wheatherapplication.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.data.dao.FavouriteWeatherDataDao
import com.example.wheatherapplication.data.map.FavouriteWeatherDataMapper
import com.example.wheatherapplication.data.map.WeatherDataMapper
import com.example.wheatherapplication.data.remote.OpenWeatherApiService
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApiService: OpenWeatherApiService,
    private val favouriteWeatherDataDao: FavouriteWeatherDataDao
) : OpenWeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeather(lat: Double, lng: Double, unit: String) =
        WeatherDataMapper.convertToWeatherData(
            openWeatherApiService.getOneCall(
                lat.toString(),
                lng.toString(),
                unit
            )
        )

    override suspend fun getForeCast(lat: Double, lng: Double) =
        openWeatherApiService.getForeCast(lat.toString(), lng.toString())


    override suspend fun getFavouriteWeathers() = flow {
        favouriteWeatherDataDao.getFavouriteWeathers().collect {
            this.emit(FavouriteWeatherDataMapper.convertToWeatherDataList(it))
        }
    }

    override suspend fun insertFavouriteWeather(weatherData: WeatherData) =
        favouriteWeatherDataDao.insertFavouriteWeather(
            FavouriteWeatherDataMapper.convertToFavouriteWeather(
                weatherData
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