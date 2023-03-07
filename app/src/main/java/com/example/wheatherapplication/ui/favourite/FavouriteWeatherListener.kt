package com.example.wheatherapplication.ui.favourite

import com.example.wheatherapplication.domain.model.WeatherData
import com.google.android.gms.maps.model.LatLng

interface FavouriteWeatherListener {
    fun onFavouriteWeatherClick(lat:Double?,lon:Double?)
    fun onDeleteFavouriteWeatherClick(weatherData: WeatherData)
}