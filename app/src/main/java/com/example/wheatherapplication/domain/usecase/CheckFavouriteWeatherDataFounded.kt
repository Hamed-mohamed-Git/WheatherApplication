package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class CheckFavouriteWeatherDataFounded @Inject constructor(
    private val repository: OpenWeatherRepository
) {
    suspend operator fun invoke(id:String):Int? =
        repository.checkFounded(id)
}