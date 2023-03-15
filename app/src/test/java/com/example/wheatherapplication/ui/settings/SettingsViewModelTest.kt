package com.example.wheatherapplication.ui.settings

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
import com.example.wheatherapplication.data.repository.fake.FakeUserSettingsPreferencesRepository
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.SetDataStoreSettingData
import com.example.wheatherapplication.ui.setting.SettingsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SettingsViewModelTest {
    private lateinit var getDataStoreSettingData: GetDataStoreSettingData
    private lateinit var setDataStoreSettingData: SetDataStoreSettingData
    private lateinit var fakeUserSettingsPreferencesRepository: UserSettingsPreferencesRepository
    private lateinit var viewModel: SettingsViewModel

    private lateinit var testContext: Context
    private lateinit var testCoroutineDispatcher: TestDispatcher
    private lateinit var testCoroutineScope: TestScope
    private lateinit var testDataStore: DataStore<Preferences>


    private fun dataStoreInit() {
        testContext = ApplicationProvider.getApplicationContext()
        testCoroutineDispatcher = StandardTestDispatcher()
        testCoroutineScope = TestScope(testCoroutineDispatcher + Job())
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile("user") }
        )
    }

    @Before
    fun initViewModel() {
        dataStoreInit()
        fakeUserSettingsPreferencesRepository = FakeUserSettingsPreferencesRepository(testDataStore)
        getDataStoreSettingData = GetDataStoreSettingData(fakeUserSettingsPreferencesRepository)
        setDataStoreSettingData = SetDataStoreSettingData(fakeUserSettingsPreferencesRepository)
        viewModel = SettingsViewModel(getDataStoreSettingData, setDataStoreSettingData)
    }

    @Test
    fun setAndGetSettings_weatherSetting_weatherSettings() = runTest {
        //Given
        val weatherSetting = WeatherSetting(
            false,
            durationTime = false,
            notificationPermission = false,
            locationType = LocationType.GPS,
            temperatureUnit = Temperature.CELSIUS,
            lengthUnit = LengthUnit.KM,
            language = Language.ARABIC
        )


        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //When
            fakeUserSettingsPreferencesRepository.setSettings(weatherSetting)

        }
        viewModel.getSettings()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //then
            viewModel.setting.collect {
                MatcherAssert.assertThat(it.language, Matchers.`is`(Language.ARABIC))
            }

        }
    }
}