package com.example.wheatherapplication.domain.model

import com.example.wheatherapplication.data.remote.dto.AlertsItem
import com.example.wheatherapplication.data.remote.dto.Temp
import com.example.wheatherapplication.data.remote.dto.WeatherItem



data class WeatherData(
    var current: CurrentWeather? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    var daily: List<WeatherDailyItem?>? = null,
    var hourly: List<WeatherHourlyItem?>? = null,
    val alerts: List<AlertsItem?>? = null,
    var address: AddressData? = null
)

data class CurrentWeather(
    val sunrise: String? = null,
    var temp: Int? = null,
    var visibility: Int? = null,
    val uvi: String? = null,
    val clouds: String? = null,
    var feelsLike: Int? = null,
    val dt: String? = null,
    val windDeg: Int? = null,
    var dewPoint: Int? = null,
    val sunset: String? = null,
    val weather: WeatherItem? = null,
    val humidity: String? = null,
    var windSpeed: Int? = null
)

data class WeatherHourlyItem(
    val hour: String? = null,
    var temp: Int? = null,
    val icon:String? = null
)

data class WeatherDailyItem(
    val day: String? = null,
    var max: Int? = null,
    var min: Int? = null,
    val icon:String? = null
)
