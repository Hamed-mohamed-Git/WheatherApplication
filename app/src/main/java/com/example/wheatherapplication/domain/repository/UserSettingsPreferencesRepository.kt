package com.example.wheatherapplication.domain.repository

import com.example.wheatherapplication.domain.model.WeatherSetting
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface UserSettingsPreferencesRepository{
    suspend fun setSettings(settings: WeatherSetting):Preferences
    suspend fun getSettings(): Flow<WeatherSetting>
}