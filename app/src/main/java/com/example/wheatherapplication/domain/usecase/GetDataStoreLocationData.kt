package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import javax.inject.Inject

class GetDataStoreLocationData @Inject constructor(
    private val repository: UserLocationPreferencesRepository
) {
    operator fun invoke() =
        repository.getDataStoreLocationData()

}