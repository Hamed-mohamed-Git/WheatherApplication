package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.data.local.UserSettingsPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserLocationPreferencesRepository
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class SetDataStoreSettingData @Inject constructor(
    private val userSettingsPreferencesRepositoryImpl: UserSettingsPreferencesRepository
) {

    suspend operator fun invoke(weatherSetting: WeatherSetting) =
        userSettingsPreferencesRepositoryImpl.setSettings(weatherSetting)
}