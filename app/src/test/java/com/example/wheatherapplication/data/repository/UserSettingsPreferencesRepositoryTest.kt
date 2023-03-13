package com.example.wheatherapplication.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.wheatherapplication.constants.Language
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.LocationType
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.local.repository.UserSettingsPreferencesRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UserSettingsPreferencesRepositoryTest {

    private lateinit var testContext: Context
    private lateinit var testCoroutineDispatcher: TestDispatcher
    private lateinit var testCoroutineScope: TestScope
    private lateinit var testDataStore: DataStore<Preferences>
    private lateinit var repository: UserSettingsPreferencesRepository


    @Before
    fun dataStore(){
        testContext = ApplicationProvider.getApplicationContext()
        testCoroutineDispatcher  = StandardTestDispatcher()
        testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile("user") }
        )
        repository = UserSettingsPreferencesRepositoryImpl(testDataStore)
    }


    @Test
    fun setAndGetSettings_weatherSettings_insertSettings() = testCoroutineScope.runTest {
        //Given
        val weatherSetting = WeatherSetting(
            false,
            LocationType.GPS,
            Temperature.CELSIUS,
            LengthUnit.KM,
            Language.ARABIC
        )

        //then
        repository.setSettings(weatherSetting)

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            repository.getSettings().collect{
                assertThat(it.language,`is`(Language.ARABIC))
            }
        }

    }
}