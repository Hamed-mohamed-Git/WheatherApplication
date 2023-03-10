package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFavouriteWeathers @Inject constructor(
    private val repository:OpenWeatherRepository
) {
    suspend operator fun invoke() = flow {
        repository.getFavouriteWeathers().collect{
            this.emit(it)
        }

    }

}