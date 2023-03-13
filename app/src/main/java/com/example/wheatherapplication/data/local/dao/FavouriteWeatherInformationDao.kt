package com.example.wheatherapplication.data.local.dao

import androidx.room.*
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteWeatherInformationDao {
    @Query("SELECT * FROM FavouriteWeatherInformation")
    fun getFavouriteWeathersInfo(): Flow<List<FavouriteWeatherInformation>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteWeatherInfo(vararg favouriteWeatherInfo: FavouriteWeatherInformation)

    @Query("SELECT * FROM FavouriteWeatherInformation WHERE lat LIKE :id ")
    fun getFavouriteWeatherInfo(id: String): Flow<FavouriteWeatherInformation>


    @Query("UPDATE FavouriteWeatherInformation SET lat = :latitude, lng = :longitude, alertStart = :alertStart, alertEnd = :alertEnd  WHERE lat = :id")
    suspend fun updateLocationFavouriteWeatherInfo(
        id: String,
        latitude: String?,
        longitude: String?,
        alertStart: Long?,
        alertEnd: Long?
    )

    @Update
    suspend fun updateFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation)

    @Delete
    suspend fun deleteFavouriteWeather(vararg favouriteWeatherInfo: FavouriteWeatherInformation)
}