package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.FavouriteWeatherInfoRepository
import javax.inject.Inject

class GetFavouriteWeathersInfo @Inject constructor(
    private val repository: FavouriteWeatherInfoRepository
) {

    suspend operator fun invoke() =
        repository.getFavoriteWeathersInfo()
}