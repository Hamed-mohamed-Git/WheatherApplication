package com.example.wheatherapplication.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.wheatherapplication.data.UserLocationPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetDataStoreLocationData @Inject constructor(
    private val userLocationPreferencesRepositoryImpl: UserLocationPreferencesRepository
) {
    suspend operator fun invoke(latLng: LatLng) =
        userLocationPreferencesRepositoryImpl.setDataStoreLocationData(latLng)

}