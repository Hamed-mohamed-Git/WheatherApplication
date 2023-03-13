package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class UpdateCurrentLocationFavouriteWeather @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    suspend operator fun invoke(
        id: String,
        favouriteWeather: FavouriteWeather
    ) =
        repository.updateCurrentLocationFavouriteWeather(
            id,
            favouriteWeather.lat,
            favouriteWeather.lng,
            favouriteWeather.weather
        )
}