package com.example.wheatherapplication.data.repository

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherInformationDao
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class FavouriteWeatherInfoRepositoryImpl @Inject constructor(
    private val favouriteWeatherInformationDao: FavouriteWeatherInformationDao
) : FavouriteWeatherInfoRepository {
    override suspend fun getFavoriteWeathersInfo() = flow {
        favouriteWeatherInformationDao.getFavouriteWeathersInfo().collect {
            this.emit(it)
        }
    }
    override suspend fun insertFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation) =
        favouriteWeatherInformationDao.insertFavouriteWeatherInfo(weatherInformation)

    override suspend fun deleteFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation) =
        favouriteWeatherInformationDao.deleteFavouriteWeather(weatherInformation)

    override suspend fun updateFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation) =
        favouriteWeatherInformationDao.updateFavouriteWeather(weatherInformation)
}