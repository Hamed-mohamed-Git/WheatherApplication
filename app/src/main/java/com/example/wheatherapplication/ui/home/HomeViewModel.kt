package com.example.wheatherapplication.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.example.wheatherapplication.domain.usecase.GetWeatherData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherData: GetWeatherData,
    private val getDataStoreLocationData: GetDataStoreLocationData
) : ViewModel() {
    private val _weatherInfo = MutableStateFlow(WeatherData())
    val weatherInfo = _weatherInfo


    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeather(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            getWeatherData(
                true, latLng.latitude,
                latLng.longitude,
                "metric",
                Temperature.CELSIUS,
                Temperature.CELSIUS,
                LengthUnit.KILOMETER
            ).collect {
                _weatherInfo.emit(it)
            }

        }
    }

//    fun getLocationWeather() {
//        viewModelScope.launch {
//            getDataStoreLocationData().collect {
//                getWeather(it)
//                getLocationInfo(it)
//            }
//        }
//    }
}