package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class SetDataStoreLocationData @Inject constructor(
    private val userLocationPreferencesRepositoryImpl: UserLocationPreferencesRepository
) {
    suspend operator fun invoke(latLng: LatLng) =
        userLocationPreferencesRepositoryImpl.setDataStoreLocationData(latLng)

}