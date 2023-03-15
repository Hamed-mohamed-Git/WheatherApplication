package com.example.wheatherapplication.ui.main_activity

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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var getDataStoreSettingData: GetDataStoreSettingData
    private lateinit var fakeUserSettingsPreferencesRepository: UserSettingsPreferencesRepository
    private lateinit var viewModel: MainViewModel

    private lateinit var testContext: Context
    private lateinit var testCoroutineDispatcher: TestDispatcher
    private lateinit var testCoroutineScope: TestScope
    private lateinit var testDataStore: DataStore<Preferences>


    private fun dataStoreInit(){
        testContext = ApplicationProvider.getApplicationContext()
        testCoroutineDispatcher  = StandardTestDispatcher()
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
        viewModel = MainViewModel(getDataStoreSettingData)
    }


    @Test
    fun collectSettings_weatherSettings() = runTest {
        //Given
        val weatherSetting = WeatherSetting(
            false,
            LocationType.GPS,
            Temperature.CELSIUS,
            LengthUnit.KM,
            Language.ARABIC
        )

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //When
            fakeUserSettingsPreferencesRepository.setSettings(weatherSetting)

            //then
            viewModel.setting.collect {
                assertThat(it.language, `is`(Language.ARABIC))
            }
        }
    }


}