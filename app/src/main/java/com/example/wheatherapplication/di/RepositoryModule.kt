package com.example.wheatherapplication.di

import com.example.wheatherapplication.data.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.data.local.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    ):OpenWeatherRepository
}