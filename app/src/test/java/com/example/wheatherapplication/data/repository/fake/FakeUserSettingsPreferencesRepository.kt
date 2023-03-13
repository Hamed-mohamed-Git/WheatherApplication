package com.example.wheatherapplication.data.repository.fake


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow


class FakeUserSettingsPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : UserSettingsPreferencesRepository {

    private val weatherSetting:MutableStateFlow<WeatherSetting> = MutableStateFlow(WeatherSetting())

    override suspend fun setSettings(settings: WeatherSetting): Preferences {
        weatherSetting.emit(settings)
        return dataStore.edit {  }
    }

    override suspend fun getSettings(): Flow<WeatherSetting> = flow {
        weatherSetting.collect{
            emit(it)
        }
    }
}



