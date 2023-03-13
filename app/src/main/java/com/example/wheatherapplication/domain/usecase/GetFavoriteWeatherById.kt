package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import javax.inject.Inject

class GetFavoriteWeatherById @Inject constructor(
    private val repository: OpenWeatherRepository
) {

    suspend operator fun invoke(id:String) =
        repository.getWeatherById(id)
}