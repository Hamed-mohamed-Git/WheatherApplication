package com.example.wheatherapplication.ui.home

import android.location.Address
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.domain.model.AddressData
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.example.wheatherapplication.domain.usecase.GetGeoCoderLocation
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository,
    private val getDataStoreLocationData: GetDataStoreLocationData,
    private val getGeoCoderLocation: GetGeoCoderLocation
) : ViewModel() {
    private val _weatherInfo = MutableStateFlow(WeatherData())
    val weatherInfo = _weatherInfo

    private val _locationInfo = MutableStateFlow(AddressData())
    val locationInfo = _locationInfo

    private fun getWeather(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherInfo.emit(
                openWeatherRepository.getWeather(
                    latLng.latitude,
                    latLng.longitude,
                    "metric"
                )
            )
        }
    }

    private fun getLocationInfo(latLng: LatLng) {
        viewModelScope.launch {
            _locationInfo.emit(getGeoCoderLocation(latLng))
        }
    }


    fun getLocationWeather() {
        viewModelScope.launch {
            getDataStoreLocationData().collect {
                getWeather(it)
                getLocationInfo(it)
            }
        }
    }
}