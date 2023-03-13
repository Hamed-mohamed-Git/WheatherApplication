package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import javax.inject.Inject

class GetFavouriteWeatherInfo @Inject constructor(
    private val repository: FavouriteWeatherInfoRepository
) {
    suspend operator fun  invoke(id: String) =
        repository.getFavoriteWeatherInfo(id)
}