package com.example.wheatherapplication.data.local.dao

import androidx.room.*
import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteWeatherInformationDao {
    @Query("SELECT * FROM FavouriteWeatherInformation")
    fun getFavouriteWeathersInfo(): Flow<List<FavouriteWeatherInformation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteWeatherInfo(vararg favouriteWeatherInfo: FavouriteWeatherInformation)

    @Update
    suspend fun updateFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation)

    @Delete
    suspend fun deleteFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation)
}