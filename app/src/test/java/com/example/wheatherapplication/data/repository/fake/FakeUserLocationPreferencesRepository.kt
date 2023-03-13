package com.example.wheatherapplication.data.repository.fake

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.flow

class FakeUserLocationPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : UserLocationPreferencesRepository {

    private val location: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))

    override fun getDataStoreLocationData(): Flow<LatLng> =  flow {
        location.collect{
            emit(it)
        }
    }

    override suspend fun setDataStoreLocationData(latLng: LatLng): Preferences {
        location.emit(latLng)
        return dataStore.edit {  }
    }
}