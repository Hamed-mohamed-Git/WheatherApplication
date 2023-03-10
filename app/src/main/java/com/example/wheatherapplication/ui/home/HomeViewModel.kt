package com.example.wheatherapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.GetFavouriteWeather
import com.example.wheatherapplication.domain.usecase.GetWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFavouriteWeather: GetFavouriteWeather,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val getWeatherData: GetWeatherData
) : ViewModel() {
    private val _weatherInfo = MutableStateFlow(WeatherData())
    val weatherInfo = _weatherInfo

    private val _weatherSetting:MutableStateFlow<WeatherSetting> = MutableStateFlow(WeatherSetting())
    val weatherSetting = _weatherSetting.asStateFlow()

    fun getFavouriteWeather(lat: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            getDataStoreSettingData().collect {
                _weatherSetting.value = it
                getFavouriteWeather(
                    lat,
                    Temperature.CELSIUS,
                    it.temperatureUnit ?: Temperature.CELSIUS,
                    it.lengthUnit ?: LengthUnit.KM
                ).collect { weatherData ->
                    _weatherInfo.emit(weatherData)
                }
            }
        }
    }

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            getDataStoreSettingData().collect {
                _weatherSetting.emit(it)
                getWeatherData(
                    lat,
                    lon,
                    Constants.API_UNIT_METRIC,
                    Temperature.CELSIUS,
                    it.temperatureUnit ?: Temperature.CELSIUS,
                    it.lengthUnit ?: LengthUnit.KM
                ).collect { weatherData ->
                    _weatherInfo.emit(weatherData)
                }

            }

        }
    }


}