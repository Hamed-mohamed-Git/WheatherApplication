package com.example.wheatherapplication.di

import com.example.wheatherapplication.data.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
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
}