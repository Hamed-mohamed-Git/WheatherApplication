package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllFavouriteWeathers @Inject constructor(
    private val repository: OpenWeatherRepository
) {
     operator fun invoke() =
        repository.getFavouriteWeathers().distinctUntilChanged().flowOn(Dispatchers.IO)

}