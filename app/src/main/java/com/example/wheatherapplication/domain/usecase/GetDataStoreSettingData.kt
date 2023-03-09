package com.example.wheatherapplication.domain.usecase

import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDataStoreSettingData @Inject constructor(
    private val userSettingsPreferencesRepositoryImpl: UserSettingsPreferencesRepository
) {

    suspend operator fun invoke() = flow {
        userSettingsPreferencesRepositoryImpl.getSettings().collect {
            this.emit(it)
        }

    }

}