package com.example.wheatherapplication.di

import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.data.remote.OpenWeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit = Retrofit
        .Builder()
        .client(okHttpClient)
        .baseUrl(Constants.API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor):OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .readTimeout(30,TimeUnit.SECONDS)
        .build()


    @Provides
    fun provideOneCallDto(retrofit: Retrofit): OpenWeatherApiService = retrofit.create(OpenWeatherApiService::class.java)

}