package com.example.wheatherapplication.data.map

import android.location.Address
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.data.remote.dto.*
import com.example.wheatherapplication.domain.model.*

object WeatherDataMapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherData(
        openWeatherOneCallDTO: OpenWeatherOneCallDTO,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit,
        address: AddressData
    ): WeatherData =
        WeatherData(
            convertToCurrentWeather(
                openWeatherOneCallDTO.current,
                currentTemperature,
                temperature,
                lengthUnit
            ),
            openWeatherOneCallDTO.lon,
            openWeatherOneCallDTO.lat,
            convertToWeatherDailyList(openWeatherOneCallDTO.daily,currentTemperature, temperature),
            convertToWeatherHourlyList(openWeatherOneCallDTO.hourly,currentTemperature, temperature),
            openWeatherOneCallDTO.alerts,
            address
        )


    private fun convertToCurrentWeather(
        current: Current?,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ) =
        current?.let {
            CurrentWeather(
                TimeMapper.convertTimeStampToHour(it.sunrise?.toLong()),
                "${TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.temp).toInt()}",
                "${
                    LengthUnitMapper.convertTo(
                        lengthUnit,
                        it.visibility?.div(1000)?.toDouble()
                    ).toInt()
                }",
                it.uvi.toString(),
                it.clouds.toString(),
                "${
                    TemperatureMapper.convertDegreeTo(
                        currentTemperature,
                        temperature,
                        it.feelsLike as Double
                    ).toInt()
                }",
                TimeMapper.convertTimeStampToFullDate(it.dt?.toLong()),
                it.windDeg.toString(),
                "${
                    TemperatureMapper.convertDegreeTo(
                        currentTemperature,
                        temperature,
                        it.dewPoint
                    ).toInt()
                }",
                TimeMapper.convertTimeStampToHour(it.sunset?.toLong()),
                WeatherItem(
                    "${Constants.API_ICON_URL}${it.weather?.get(0)?.icon}@2x.png",
                    it.weather?.get(0)?.description,
                    it.weather?.get(0)?.main,
                    it.weather?.get(0)?.id
                ),
                "${it.humidity}",
                "${LengthUnitMapper.convertTo(lengthUnit, it.windSpeed as Double).toInt()}"
            )
        }


    private fun convertToWeatherHourlyItem(hourlyItem: HourlyItem, index: Int,currentTemperature: Temperature,
                                           temperature: Temperature,): WeatherHourlyItem =
        WeatherHourlyItem(
            if (index > 0) TimeMapper.convertTimeStampToHour(hourlyItem.dt?.toLong()).trimStart('0')
            else "Now",
            "${TemperatureMapper.convertDegreeTo(currentTemperature,temperature,hourlyItem.temp).toInt()}",
            "${Constants.API_ICON_URL}${hourlyItem.weather?.get(0)?.icon}@2x.png"
        )


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyItem(dailyItem: DailyItem, index: Int,currentTemperature: Temperature,
                                  temperature: Temperature,) =
        WeatherDailyItem(
            if (index > 0) TimeMapper.convertTimeStampToLocalDate(dailyItem.dt?.toLong()).dayOfWeek.toString()
                .lowercase().replaceFirstChar {
                    it.uppercase()
                }.substring(0, 3) else "Today",
            "${TemperatureMapper.convertDegreeTo(currentTemperature,temperature,dailyItem.temp?.max).toInt()}",
            "${TemperatureMapper.convertDegreeTo(currentTemperature,temperature,dailyItem.temp?.min).toInt()}",
            "${Constants.API_ICON_URL}${dailyItem.weather?.get(0)?.icon}@2x.png"
        )


    private fun convertToWeatherHourlyList(hourlyList: List<HourlyItem?>?,currentTemperature: Temperature,
                                           temperature: Temperature,): List<WeatherHourlyItem> {
        val weatherHourlyList: MutableList<WeatherHourlyItem> = mutableListOf()
        hourlyList?.let {
            repeat(hourlyList.size) { index ->
                hourlyList[index]?.let { hourlyItem ->
                    weatherHourlyList.add(convertToWeatherHourlyItem(hourlyItem, index,currentTemperature, temperature))
                }
            }
        }
        return weatherHourlyList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyList(dailyList: List<DailyItem?>?,currentTemperature: Temperature,
                                  temperature: Temperature,): List<WeatherDailyItem> {
        val weatherDailyList: MutableList<WeatherDailyItem> = mutableListOf()
        dailyList?.let {
            repeat(dailyList.size) { index ->
                dailyList[index]?.let { dailyItem ->
                    weatherDailyList.add(convertToWeatherDailyItem(dailyItem, index,currentTemperature, temperature))
                }
            }
        }
        return weatherDailyList
    }


}