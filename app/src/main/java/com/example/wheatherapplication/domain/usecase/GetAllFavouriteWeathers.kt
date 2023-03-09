package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class GetAllFavouriteWeathers @Inject constructor(
    private val repository:OpenWeatherRepository
) {
    suspend operator fun invoke() =
        repository.getFavouriteWeathers()
}