package com.example.wheatherapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.LocationType
import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.data.local.FavouriteWeatherInformation
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.domain.usecase.*
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BaseWeatherViewModel @Inject constructor(
    private val getAllFavouriteWeathers: GetAllFavouriteWeathers,
    private val getDataStoreSettingData: GetDataStoreSettingData,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val updateCurrentLocationWeatherInfo: UpdateCurrentLocationWeatherInfo,
    private val getCurrentLocation: GetCurrentLocation,
    private val setDataStoreLocationData: SetDataStoreLocationData,
    private val getFavouriteWeatherInfo: GetFavouriteWeatherInfo,
    private val updateCurrentLocationFavouriteWeather: UpdateCurrentLocationFavouriteWeather,
    private val getFavoriteWeatherById: GetFavoriteWeatherById
) : ViewModel() {
    private val _favouriteWeathers: MutableStateFlow<List<WeatherData>> =
        MutableStateFlow(emptyList())
    var favouriteWeathers = _favouriteWeathers.asLiveData()

    private val _settings: MutableStateFlow<WeatherSetting> = MutableStateFlow(WeatherSetting())
    val settings = _settings.asLiveData()

    init {
        viewModelScope.launch {
            getDataStoreSettingData().collect { weatherSetting ->
                _settings.emit(weatherSetting)
                if (weatherSetting.locationType == LocationType.GPS) {
                    getDataStoreLocationData().collect { oldLatLng ->
                        getCurrentLocation().collect { location ->
                            getFavouriteWeatherInfo(oldLatLng.latitude.toString()).collect {
                                updateCurrentLocationWeatherInfo(
                                    oldLatLng.latitude.toString(),
                                    FavouriteWeatherInformation(
                                        location.latitude.toString(),
                                        location.longitude.toString(),
                                        it.alertStart ?: 0L,
                                        it.alertEnd ?: 0L
                                    )
                                )
                            }
                            getFavoriteWeatherById(oldLatLng.latitude.toString()).collect {
                                updateCurrentLocationFavouriteWeather(
                                    it.lat,
                                    FavouriteWeather(
                                        location.latitude.toString(),
                                        location.longitude.toString(),
                                        it.weather
                                    )
                                )
                            }
                            setDataStoreLocationData(LatLng(location.latitude, location.longitude))
                        }

                    }
                }
            }

        }

    }


     fun getFavouriteWeathers() {
        viewModelScope.launch {
            getAllFavouriteWeathers().onEach {
                _favouriteWeathers.emit(it)
            }.launchIn(this)
        }
    }


}