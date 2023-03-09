package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import javax.inject.Inject

class GetDataStoreSettingData @Inject constructor(
    private val userSettingsPreferencesRepositoryImpl: UserSettingsPreferencesRepository
) {

    suspend operator fun invoke() =
        userSettingsPreferencesRepositoryImpl.getSettings()
}