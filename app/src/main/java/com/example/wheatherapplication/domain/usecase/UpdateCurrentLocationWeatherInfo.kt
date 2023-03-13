package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import javax.inject.Inject

class UpdateCurrentLocationWeatherInfo @Inject constructor(
    private val repository: FavouriteWeatherInfoRepository
) {
    suspend operator fun invoke(
        id: String,
        favouriteWeatherInformation: FavouriteWeatherInformation
    ) =
        repository.updateLocationFavouriteWeatherInfo(id, favouriteWeatherInformation)
}