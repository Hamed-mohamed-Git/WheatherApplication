package com.example.wheatherapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.data.dto.OpenWeatherDTO
import com.example.wheatherapplication.domain.repository.OpenWeatherRepository
import com.example.wheatherapplication.domain.usecase.GetDataStoreLocationData
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository,
    private val getDataStoreLocationData: GetDataStoreLocationData
):ViewModel() {
    private val _weatherInfo = MutableStateFlow(OpenWeatherDTO())
    val weatherInfo = _weatherInfo

    private fun getWeather(latLng: LatLng){
        viewModelScope.launch(Dispatchers.IO) {
            _weatherInfo.emit(openWeatherRepository.getWeather(latLng.latitude,latLng.longitude))
        }
    }

    fun getLocationWeather(){
        viewModelScope.launch {
            getDataStoreLocationData().collect{
                getWeather(it)
            }
        }
    }
}