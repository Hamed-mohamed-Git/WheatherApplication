package com.example.wheatherapplication.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface UserLocationPreferencesRepository {
    fun getDataStoreLocationData(): Flow<LatLng>
    suspend fun setDataStoreLocationData(latLng: LatLng): Preferences
}