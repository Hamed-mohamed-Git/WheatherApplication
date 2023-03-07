package com.example.wheatherapplication.di

import android.content.Context
import androidx.room.Room
import com.example.wheatherapplication.data.local.dao.FavouriteWeatherDataDao
import com.example.wheatherapplication.data.local.AppDatabase
import com.example.wheatherapplication.domain.model.WeatherData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDataBase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "weather Database")
            .build()

    @Provides
    fun provideFavouriteWeatherDataDao(appDatabase: AppDatabase): FavouriteWeatherDataDao =
        appDatabase.favouriteWeatherDataDao()
}