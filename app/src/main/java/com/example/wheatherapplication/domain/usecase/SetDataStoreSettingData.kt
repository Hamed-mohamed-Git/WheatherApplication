package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import javax.inject.Inject

class SetDataStoreSettingData @Inject constructor(
    private val userSettingsPreferencesRepositoryImpl: UserSettingsPreferencesRepository
) {

    suspend operator fun invoke(weatherSetting: WeatherSetting) =
        userSettingsPreferencesRepositoryImpl.setSettings(weatherSetting)
}