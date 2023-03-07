package com.example.wheatherapplication.data.local.dao

import androidx.room.*
import com.example.wheatherapplication.data.local.FavouriteWeather
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteWeatherDataDao {
    @Query("SELECT * FROM FavouriteWeather")
    fun getFavouriteWeathers(): Flow<List<FavouriteWeather>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Query("SELECT * FROM FavouriteWeather WHERE latLngID LIKE :id ")
    fun getFavouriteWeather(id:String):Flow<FavouriteWeather>

    @Update
    suspend fun updateFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Delete
    suspend fun deleteFavouriteWeather(vararg favouriteWeather: FavouriteWeather)
}