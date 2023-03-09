package com.example.wheatherapplication.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.usecase.DeleteWeatherData
import com.example.wheatherapplication.domain.usecase.GetAllFavouriteWeathers
import com.example.wheatherapplication.domain.usecase.GetDataStoreSettingData
import com.example.wheatherapplication.domain.usecase.SaveWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val saveWeatherData: SaveWeatherData,
    private val deleteWeatherData: DeleteWeatherData,
    private val getAllFavouriteWeathers: GetAllFavouriteWeathers,
    private val getDataStoreSettingData: GetDataStoreSettingData
) : ViewModel() {

    private val _favouriteWeathers: MutableStateFlow<List<WeatherData>> =
        MutableStateFlow(emptyList())
    val favouriteWeathers = _favouriteWeathers

    fun getFavouriteWeathers() {
        viewModelScope.launch {
            getAllFavouriteWeathers().collect {
                _favouriteWeathers.emit(it)
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
                    it.lengthUnit ?: LengthUnit.KILOMETER
                )
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
}