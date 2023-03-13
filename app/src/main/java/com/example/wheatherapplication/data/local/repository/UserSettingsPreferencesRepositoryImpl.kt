package com.example.wheatherapplication.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.wheatherapplication.constants.*
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserSettingsPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserSettingsPreferencesRepository {

    override suspend fun setSettings(settings: WeatherSetting) =
        dataStore.edit {
            it[stringPreferencesKey(Constants.NOTIFICATION_PERMISSION_TYPE_DATASTORE_KEY)] =
                settings.notificationPermission.toString()
            it[stringPreferencesKey(Constants.LOCATION_TYPE_DATASTORE_KEY)] =
                settings.locationType.toString()
            it[stringPreferencesKey(Constants.DEGREE_UNIT_DATASTORE_KEY)] =
                settings.temperatureUnit.toString()
            it[stringPreferencesKey(Constants.LENGTH_UNIT_DATASTORE_KEY)] =
                settings.lengthUnit.toString()
            it[stringPreferencesKey(Constants.LANGUAGE_DATASTORE_KEY)] =
                settings.language.toString()
        }

    override suspend fun getSettings() = dataStore.data.map {
        WeatherSetting(
            it[stringPreferencesKey(Constants.NOTIFICATION_PERMISSION_TYPE_DATASTORE_KEY)].toBoolean(),
            enumValueOf<LocationType>(it[stringPreferencesKey(Constants.LOCATION_TYPE_DATASTORE_KEY)].toString()),
            enumValueOf<Temperature>(it[stringPreferencesKey(Constants.DEGREE_UNIT_DATASTORE_KEY)].toString()),
            enumValueOf<LengthUnit>(it[stringPreferencesKey(Constants.LENGTH_UNIT_DATASTORE_KEY)].toString()),
            enumValueOf<Language>(it[stringPreferencesKey(Constants.LANGUAGE_DATASTORE_KEY)].toString())
        )
    }
}