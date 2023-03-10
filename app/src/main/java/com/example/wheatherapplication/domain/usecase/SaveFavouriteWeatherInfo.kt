package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import javax.inject.Inject

class SaveFavouriteWeatherInfo @Inject constructor(
    private val repository: FavouriteWeatherInfoRepository
) {
    suspend operator fun invoke(weatherInformation: FavouriteWeatherInformation) =
        repository.insertFavouriteWeatherInfo(weatherInformation)
}