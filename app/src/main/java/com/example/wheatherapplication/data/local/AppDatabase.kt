package com.example.wheatherapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherDataDao
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherInformationDao

@Database(entities = [FavouriteWeather::class, FavouriteWeatherInformation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteWeatherDataDao(): FavouriteWeatherDataDao
    abstract fun favouriteWeatherInfoDao(): FavouriteWeatherInformationDao
}