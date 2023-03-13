package com.example.wheatherapplication.data.local.dao.fake

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherInformationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFavouriteWeatherInformationDao : FavouriteWeatherInformationDao {
    private val favouriteWeatherInformation: MutableList<FavouriteWeatherInformation> =
        mutableListOf()

    override fun getFavouriteWeathersInfo(): Flow<List<FavouriteWeatherInformation>> = flow {
        emit(favouriteWeatherInformation)
    }

    override suspend fun insertFavouriteWeatherInfo(vararg favouriteWeatherInfo: FavouriteWeatherInformation) {
        repeat(favouriteWeatherInfo.size) {
            favouriteWeatherInformation.add(favouriteWeatherInfo[it])
        }
    }

    override fun getFavouriteWeatherInfo(id: String): Flow<FavouriteWeatherInformation> = flow {
        favouriteWeatherInformation.find {
            it.lat == id
        }?.let {
            emit(it)
        }

    }

    override suspend fun updateLocationFavouriteWeatherInfo(
        id: String,
        latitude: String?,
        longitude: String?,
        alertStart: Long?,
        alertEnd: Long?
    ) {
        favouriteWeatherInformation.find {
            it.lat == id
        }?.let {
            favouriteWeatherInformation.remove(it)
            favouriteWeatherInformation.add(
                FavouriteWeatherInformation(
                    latitude ?: "",
                    longitude ?: "",
                    alertStart, alertEnd
                )
            )
        }

    }

    override suspend fun updateFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation) {
        favouriteWeatherInformation.find {
            it.lat == favouriteWeatherInfo[0].lat
        }?.let {
            favouriteWeatherInformation.remove(it)
            favouriteWeatherInformation.add(favouriteWeatherInfo[0])
        }
    }

    override suspend fun deleteFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation) {
        favouriteWeatherInformation.find {
            it.lat == favouriteWeatherInfo[0].lat
        }?.let {
            favouriteWeatherInformation.remove(it)
        }
    }

}