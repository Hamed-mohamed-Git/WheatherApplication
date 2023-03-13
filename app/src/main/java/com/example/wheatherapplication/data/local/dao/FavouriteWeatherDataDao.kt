package com.example.wheatherapplication.data.local.dao

import androidx.room.*
import com.example.wheatherapplication.data.local.FavouriteWeather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Dao
interface FavouriteWeatherDataDao {
    @Query("SELECT * FROM FavouriteWeather")
    fun getFavouriteWeathers(): Flow<List<FavouriteWeather>>


    @Query("SELECT 1 FROM FavouriteWeather WHERE lat LIKE :id ")
    suspend fun checkFounded(id: String): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Query("UPDATE FavouriteWeather SET lat = :latitude, lng = :longitude, weather = :weather  WHERE lat = :id")
    suspend fun updateLocationFavouriteWeather(
        id: String,
        latitude: String?,
        longitude: String?,
        weather: String?,
    )

    @Query("SELECT * FROM FavouriteWeather WHERE lat LIKE :id ")
    fun getFavouriteWeather(id: String): Flow<FavouriteWeather>

    @Update
    suspend fun updateFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Delete
    suspend fun deleteFavouriteWeather(vararg favouriteWeather: FavouriteWeather)
}