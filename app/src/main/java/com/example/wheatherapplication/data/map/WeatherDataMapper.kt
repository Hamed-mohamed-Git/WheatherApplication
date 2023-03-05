package com.example.wheatherapplication.data.map

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.data.dto.*
import com.example.wheatherapplication.domain.model.CurrentWeather
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherHourlyItem

object WeatherDataMapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherData(openWeatherOneCallDTO: OpenWeatherOneCallDTO): WeatherData =
        WeatherData(
            convertToCurrentWeather(openWeatherOneCallDTO.current),
            openWeatherOneCallDTO.lon,
            openWeatherOneCallDTO.lat,
            convertToWeatherDailyList(openWeatherOneCallDTO.daily),
            convertToWeatherHourlyList(openWeatherOneCallDTO.hourly),
            openWeatherOneCallDTO.alerts
        )


    private fun convertToCurrentWeather(current: Current?) =
        CurrentWeather(
            TimeMapper.convertTimeStampToHour(current?.sunrise?.toLong()),
            "${current?.temp?.toInt()}°",
            "${current?.visibility?.div(1000)} km",
            current?.uvi.toString(),
            current?.clouds.toString(),
            "${current?.feelsLike}°",
            TimeMapper.convertTimeStampToFullDate(current?.dt?.toLong()),
            current?.windDeg.toString(),
            "The dew point is ${current?.dewPoint}° right now.",
            "Sunrise: ${TimeMapper.convertTimeStampToHour(current?.sunset?.toLong())}",
            WeatherItem(
                "${Constants.API_ICON_URL}${current?.weather?.get(0)?.icon}@2x.png",
                current?.weather?.get(0)?.description,
                current?.weather?.get(0)?.main,
                current?.weather?.get(0)?.id
            ),
            "${current?.humidity}%",
            "${current?.windSpeed}"
        )


    private fun convertToWeatherHourlyItem(hourlyItem: HourlyItem, index: Int): WeatherHourlyItem =
        WeatherHourlyItem(
            if (index > 0) TimeMapper.convertTimeStampToHour(hourlyItem.dt?.toLong()).trimStart('0')
            else "Now",
            "${hourlyItem.temp?.toInt()}°",
            "${Constants.API_ICON_URL}${hourlyItem.weather?.get(0)?.icon}@2x.png"
        )


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyItem(dailyItem: DailyItem, index: Int) =
        WeatherDailyItem(
            if (index > 0) TimeMapper.convertTimeStampToLocalDate(dailyItem.dt?.toLong()).dayOfWeek.toString()
                .lowercase().replaceFirstChar {
                    it.uppercase()
                }.substring(0, 3) else "Today",
            "${dailyItem.temp?.max?.toInt()}°",
            "${dailyItem.temp?.min?.toInt()}°",
            "${Constants.API_ICON_URL}${dailyItem.weather?.get(0)?.icon}@2x.png"
        )


    private fun convertToWeatherHourlyList(hourlyList: List<HourlyItem?>?): List<WeatherHourlyItem> {
        val weatherHourlyList: MutableList<WeatherHourlyItem> = mutableListOf()
        hourlyList?.let {
            repeat(hourlyList.size) { index ->
                hourlyList[index]?.let { hourlyItem ->
                    weatherHourlyList.add(convertToWeatherHourlyItem(hourlyItem, index))
                }
            }
        }
        return weatherHourlyList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyList(dailyList: List<DailyItem?>?): List<WeatherDailyItem> {
        val weatherDailyList: MutableList<WeatherDailyItem> = mutableListOf()
        dailyList?.let {
            repeat(dailyList.size) { index ->
                dailyList[index]?.let { dailyItem ->
                    weatherDailyList.add(convertToWeatherDailyItem(dailyItem, index))
                }
            }
        }
        return weatherDailyList
    }


}