package com.example.wheatherapplication.data.dao

import androidx.room.*
import com.example.wheatherapplication.data.local.FavouriteWeather
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouriteWeatherDataDao {
    @Query("SELECT * FROM FavouriteWeather")
    fun getFavouriteWeathers(): Flow<List<FavouriteWeather>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Update
    suspend fun updateFavouriteWeather(vararg favouriteWeather: FavouriteWeather)

    @Delete
    suspend fun deleteFavouriteWeather(vararg favouriteWeather: FavouriteWeather)
}