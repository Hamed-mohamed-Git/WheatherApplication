package com.example.wheatherapplication.data.map

import com.example.wheatherapplication.data.local.FavouriteWeather
import com.example.wheatherapplication.domain.model.WeatherData
import com.google.gson.Gson


object FavouriteWeatherDataMapper {
    private val gson: Gson by lazy {
        Gson()
    }

    private fun convertToJson(weatherData: WeatherData?): String =
        gson.toJson(weatherData)

    fun convertToWeatherData(favouriteWeatherDataJson: String?): WeatherData =
        gson.fromJson(favouriteWeatherDataJson, WeatherData::class.java)


    fun convertToFavouriteWeather(weatherData: WeatherData?): FavouriteWeather =
        FavouriteWeather(
            "${weatherData?.lat}-${weatherData?.lon}",
            convertToJson(weatherData)
        )

    fun convertToWeatherDataList(favouriteWeatherDataList: List<FavouriteWeather>?): List<WeatherData> {
        val weatherDataList: MutableList<WeatherData> = mutableListOf()
        favouriteWeatherDataList?.forEach {
            weatherDataList.add(convertToWeatherData(it.weather))
        }
        return weatherDataList
    }


}