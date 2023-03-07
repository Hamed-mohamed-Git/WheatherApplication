package com.example.wheatherapplication.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapplication.data.OpenWeatherRepositoryImpl
import com.example.wheatherapplication.domain.model.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val openWeatherRepositoryImpl: OpenWeatherRepositoryImpl
):ViewModel() {

    private val _favouriteWeathers: MutableStateFlow<List<WeatherData>> =
        MutableStateFlow(emptyList())
    val favouriteWeathers = _favouriteWeathers

    fun getFavouriteWeathers() {
        viewModelScope.launch {
            openWeatherRepositoryImpl.getFavouriteWeathers().collect {
                _favouriteWeathers.emit(it)
            }
        }
    }

    fun saveFavouriteWeather(weatherData: WeatherData){
        viewModelScope.launch {
            openWeatherRepositoryImpl.insertFavouriteWeather(weatherData)
        }
    }

    fun deleteFavouriteWeather(weatherDataList: List<WeatherData>){
        viewModelScope.launch {
            repeat(weatherDataList.size){
                openWeatherRepositoryImpl.deleteFavouriteWeather(weatherDataList[it])
            }
        }
    }
}