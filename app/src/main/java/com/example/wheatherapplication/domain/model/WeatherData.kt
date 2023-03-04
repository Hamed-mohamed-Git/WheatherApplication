package com.example.wheatherapplication.domain.model

import com.example.wheatherapplication.data.dto.AlertsItem
import com.example.wheatherapplication.data.dto.Temp
import com.example.wheatherapplication.data.dto.WeatherItem



data class WeatherData(
    val current: CurrentWeather? = null,
    val lon: Double? = null,
    val lat: Double? = null,
    val daily: List<WeatherDailyItem?>? = null,
    val hourly: List<WeatherHourlyItem?>? = null,
    val alerts: List<AlertsItem?>? = null
)

data class CurrentWeather(
    val sunrise: String? = null,
    val temp: String? = null,
    val visibility: String? = null,
    val uvi: String? = null,
    val clouds: String? = null,
    val feelsLike: String? = null,
    val dt: String? = null,
    val windDeg: String? = null,
    val dewPoint: String? = null,
    val sunset: String? = null,
    val weather: WeatherItem? = null,
    val humidity: String? = null,
    val windSpeed: String? = null
)

data class WeatherHourlyItem(
    val hour: String? = null,
    val temp: String? = null,
    val icon:String? = null
)

data class WeatherDailyItem(
    val day: String? = null,
    val max: String? = null,
    val min: String? = null,
    val icon:String? = null
)
