package com.example.wheatherapplication.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocationPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserLocationPreferencesRepository {
    override fun getDataStoreLocationData() = dataStore.data.map {
        LatLng((it[stringPreferencesKey("lat")]?.toDouble() ?: 0.0),
            (it[stringPreferencesKey("lng")]?.toDouble() ?: 0.0)
        )
    }

    override suspend fun setDataStoreLocationData(latLng: LatLng) = dataStore.edit {
        it[stringPreferencesKey("lat")] = latLng.latitude.toString()
        it[stringPreferencesKey("lng")] = latLng.longitude.toString()
    }
}