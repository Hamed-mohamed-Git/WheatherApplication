package com.example.wheatherapplication.ui.home

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
import com.example.wheatherapplication.data.repository.fake.FakeOpenWeatherRepository
import com.example.wheatherapplication.data.repository.fake.FakeUserSettingsPreferencesRepository
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.repository.UserSettingsPreferencesRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.GetFavouriteWeather
import com.example.wheatherapplication.domain.usecase.GetWeatherData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var getDataStoreSettingData: GetDataStoreSettingData
    private lateinit var getWeatherData: GetWeatherData
    private lateinit var getFavouriteWeather: GetFavouriteWeather
    private lateinit var fakeUserSettingsPreferencesRepository: UserSettingsPreferencesRepository
    private lateinit var fakeOpenWeatherRepository: OpenWeatherRepository
    private lateinit var viewModel: HomeViewModel

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
        fakeOpenWeatherRepository = FakeOpenWeatherRepository()
        fakeUserSettingsPreferencesRepository = FakeUserSettingsPreferencesRepository(testDataStore)
        getDataStoreSettingData = GetDataStoreSettingData(fakeUserSettingsPreferencesRepository)
        getWeatherData = GetWeatherData(fakeOpenWeatherRepository)
        getFavouriteWeather = GetFavouriteWeather(fakeOpenWeatherRepository)
        viewModel = HomeViewModel(getFavouriteWeather, getDataStoreSettingData, getWeatherData)
    }

    @Test
    fun getWeather_WeatherData() = runTest {

        //Given
        val weatherData = WeatherData(lon = 20.0, lat = 50.0)


        //When

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {

            fakeOpenWeatherRepository.insertFavouriteWeather(
                weatherData,
                Temperature.CELSIUS,
                Temperature.CELSIUS,
                LengthUnit.KM
            )
        }

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getFavouriteWeather(50.0)
        }

        //Then
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.weatherInfo.collect {
                assertThat(it.lat, `is`(50.0))
            }

        }
    }

    @Test
    fun getWeatherSettings_WeatherSettings() = runTest {
        //Given
        val weatherSetting = WeatherSetting(
            severeWeather = false,
            durationTime = false,
            notificationPermission = false,
            locationType = LocationType.GPS,
            temperatureUnit = Temperature.CELSIUS,
            lengthUnit = LengthUnit.KM,
            language = Language.ARABIC
        )

        //When
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            fakeUserSettingsPreferencesRepository.setSettings(weatherSetting)
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getWeather(20.0,20.0)
        }




        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            //then
            viewModel.weatherSetting.collect {
                assertThat(it.language, `is`(Language.ARABIC))
            }

        }
    }
}