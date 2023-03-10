package com.example.wheatherapplication.di

import com.example.wheatherapplication.data.repository.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.data.local.repository.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.data.local.repository.UserSettingsPreferencesRepositoryImpl
import com.example.wheatherapplication.data.repository.FavouriteWeatherInfoRepositoryImpl
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserLocationPrefRepositoryImpl(
        userLocationPreferencesRepositoryImpl: UserLocationPreferencesRepositoryImpl
    ): UserLocationPreferencesRepository

    @Binds
    abstract fun bindOpenWeatherRepositoryImpl(
        openWeatherRepositoryImpl: OpenWeatherRepositoryImpl
    ): OpenWeatherRepository

    @Binds
    abstract fun bingUserSettingsPreferencesRepository(
        userSettingsPreferencesRepositoryImpl: UserSettingsPreferencesRepositoryImpl
    ): UserSettingsPreferencesRepository

    @Binds
    abstract fun bindFavouriteWeatherInfoRepositoryImpl(
        favouriteWeatherInfoRepositoryImpl: FavouriteWeatherInfoRepositoryImpl
    ):FavouriteWeatherInfoRepository
}