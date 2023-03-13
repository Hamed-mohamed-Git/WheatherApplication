package com.example.wheatherapplication.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.data.map.WeatherDataMapper
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val saveWeatherData: SaveFavouriteWeatherData,
    private val deleteWeatherData: DeleteWeatherData,
    private val saveFavouriteWeatherInfo: SaveFavouriteWeatherInfo,
    private val getAllFavouriteWeathers: GetAllFavouriteWeathers,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val settingData: SetDataStoreSettingData,
    private val enqueueWarningAlertWorkManger: EnqueueWarningAlertWorkManger
) : ViewModel() {

    private val _favouriteWeathers: MutableStateFlow<List<WeatherData>> =
        MutableStateFlow(emptyList())
    val favouriteWeathers = _favouriteWeathers

    private val _settings = MutableStateFlow(WeatherSetting())
    val setting = _settings.asStateFlow()

    fun getFavouriteWeathers() {
        viewModelScope.launch {
            getDataStoreSettingData().collect {
                _settings.emit(it)
                getAllFavouriteWeathers().collect { list ->
                    _favouriteWeathers.emit(
                        WeatherDataMapper.convertTListWeatherData(
                            list,
                            Temperature.CELSIUS,
                            it.temperatureUnit ?: Temperature.CELSIUS,
                            it.lengthUnit ?: LengthUnit.KM
                        )
                    )
                }
            }

        }
    }

    fun saveFavouriteWeather(weatherData: WeatherData) {
        viewModelScope.launch {
            getDataStoreSettingData().collect {
                saveWeatherData(
                    weatherData,
                    it.temperatureUnit ?: Temperature.CELSIUS,
                    Temperature.CELSIUS,
                    it.lengthUnit ?: LengthUnit.KM
                )
                saveFavouriteWeatherInfo(
                    FavouriteWeatherInformation(
                        weatherData.lat.toString(),
                        weatherData.lon.toString(),
                        0L,
                        0L
                    )
                )
                getFavouriteWeathers()
            }

        }
    }

    fun deleteFavouriteWeather(weatherDataList: List<WeatherData>) {
        viewModelScope.launch {
            repeat(weatherDataList.size) {
                deleteWeatherData(weatherDataList[it])
            }
        }
    }

    fun setNotificationPermission(setting: WeatherSetting) =
        viewModelScope.launch {
            settingData(
                WeatherSetting(
                    true,
                    setting.locationType,
                    setting.temperatureUnit,
                    setting.lengthUnit,
                    setting.language,
                )
            )
        }


    fun enqueueAlert(interval: Long) {
        enqueueWarningAlertWorkManger(interval)
    }
}