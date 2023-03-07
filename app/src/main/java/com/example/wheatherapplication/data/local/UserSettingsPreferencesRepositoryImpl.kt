package com.example.wheatherapplication.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserSettingsPreferencesRepository {

    override suspend fun setSettings(settings: WeatherSetting) =
        dataStore.edit {
            it[stringPreferencesKey(Constants.DEGREE_UNIT_DATASTORE_KEY)] =
                settings.temperatureUnit.toString()
            it[stringPreferencesKey(Constants.LENGTH_UNIT_DATASTORE_KEY)] =
                settings.lengthUnit.toString()
        }

    override suspend fun getSettings() = dataStore.data.map {
        WeatherSetting(
            it[stringPreferencesKey(Constants.DEGREE_UNIT_DATASTORE_KEY)] as Temperature,
            it[stringPreferencesKey(Constants.LENGTH_UNIT_DATASTORE_KEY)] as LengthUnit
        )
    }
}