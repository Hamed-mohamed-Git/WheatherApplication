package com.example.wheatherapplication.domain.repository

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import kotlinx.coroutines.flow.Flow

interface FavouriteWeatherInfoRepository {
    suspend fun getFavoriteWeathersInfo(): Flow<List<FavouriteWeatherInformation>>

    suspend fun insertFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation)

    suspend fun deleteFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation)

    suspend fun updateFavouriteWeatherInfo(weatherInformation: FavouriteWeatherInformation)
}