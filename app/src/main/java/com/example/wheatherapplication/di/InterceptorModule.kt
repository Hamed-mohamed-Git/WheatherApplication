package com.example.wheatherapplication.di

import com.example.wheatherapplication.data.remote.interceptors.OpenWeatherInterceptor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
abstract class InterceptorModule {
    @Binds
    abstract fun bindsInterceptor(
        openWeatherInterceptor: OpenWeatherInterceptor
    ):Interceptor
}