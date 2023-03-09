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
            convertToWeatherDailyList(openWeatherOneCallDTO.daily, currentTemperature, temperature),
            convertToWeatherHourlyList(
                openWeatherOneCallDTO.hourly,
                currentTemperature,
                temperature
            ),
            openWeatherOneCallDTO.alerts,
            address
        )


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherData(
        weatherData: WeatherData,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): WeatherData =
        weatherData.let {
            it.current = convertCurrentToCurrentWeather(
                it.current,
                currentTemperature,
                temperature,
                lengthUnit
            )
            it.daily = convertWeatherToWeatherDailyList(it.daily!!.toMutableList(),currentTemperature, temperature)
            it.hourly = convertWeatherToWeatherHourlyList(it.hourly!!.toMutableList(),currentTemperature, temperature)
        } as WeatherData


    private fun convertToCurrentWeather(
        current: Current?,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ) =
        current?.let {
            CurrentWeather(
                TimeMapper.convertTimeStampToHour(it.sunrise?.toLong()),
                TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.temp).toInt(),
                LengthUnitMapper.convertTo(
                    lengthUnit,
                    it.visibility?.div(1000)?.toDouble()
                ).toInt(),
                it.uvi.toString(),
                it.clouds.toString(),

                TemperatureMapper.convertDegreeTo(
                    currentTemperature,
                    temperature,
                    it.feelsLike as Double
                ).toInt(),
                TimeMapper.convertTimeStampToFullDate(it.dt?.toLong()),
                it.windDeg,

                TemperatureMapper.convertDegreeTo(
                    currentTemperature,
                    temperature,
                    it.dewPoint
                ).toInt(),
                TimeMapper.convertTimeStampToHour(it.sunset?.toLong()),
                WeatherItem(
                    "${Constants.API_ICON_URL}${it.weather?.get(0)?.icon}@2x.png",
                    it.weather?.get(0)?.description,
                    it.weather?.get(0)?.main,
                    it.weather?.get(0)?.id
                ),
                "${it.humidity}",
                LengthUnitMapper.convertTo(lengthUnit, it.windSpeed as Double).toInt()
            )
        }

    private fun convertCurrentToCurrentWeather(
        current: CurrentWeather?,
        currentTemperature: Temperature,
        temperature: Temperature,
        lengthUnit: LengthUnit
    ): CurrentWeather {
        return current?.let {
            it.visibility = LengthUnitMapper.convertTo(lengthUnit, it.visibility?.toDouble()).toInt()
            it.dewPoint =
                TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.dewPoint?.toDouble()).toInt()
            it.temp = TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.temp?.toDouble()).toInt()
            it.windSpeed = LengthUnitMapper.convertTo(lengthUnit, it.windSpeed?.toDouble()).toInt()
            it.feelsLike =
                TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.feelsLike?.toDouble()).toInt()
        } as CurrentWeather
    }


    private fun convertToWeatherHourlyItem(
        hourlyItem: HourlyItem, index: Int, currentTemperature: Temperature,
        temperature: Temperature,
    ): WeatherHourlyItem =
        WeatherHourlyItem(
            if (index > 0) TimeMapper.convertTimeStampToHour(hourlyItem.dt?.toLong())
                .trimStart('0')
            else "Now",
            TemperatureMapper.convertDegreeTo(currentTemperature, temperature, hourlyItem.temp).toInt(),
            "${Constants.API_ICON_URL}${hourlyItem.weather?.get(0)?.icon}@2x.png"
        )

    private fun convertToWeatherHourlyItem(
        hourlyItem: WeatherHourlyItem, index: Int, currentTemperature: Temperature,
        temperature: Temperature,
    ): WeatherHourlyItem = hourlyItem.let {
        it.temp = TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.temp?.toDouble()).toInt()
    } as WeatherHourlyItem


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyItem(
        dailyItem: DailyItem, index: Int, currentTemperature: Temperature,
        temperature: Temperature,
    ) =
        WeatherDailyItem(
            if (index > 0) TimeMapper.convertTimeStampToLocalDate(dailyItem.dt?.toLong()).dayOfWeek.toString()
                .lowercase().replaceFirstChar {
                    it.uppercase()
                }.substring(0, 3) else "Today",

            TemperatureMapper.convertDegreeTo(
                currentTemperature,
                temperature,
                dailyItem.temp?.max
            ).toInt(),

            TemperatureMapper.convertDegreeTo(
                currentTemperature,
                temperature,
                dailyItem.temp?.min
            ).toInt(),
            "${Constants.API_ICON_URL}${dailyItem.weather?.get(0)?.icon}@2x.png"
        )


    fun convertToWeatherDailyItem(
        dailyItem: WeatherDailyItem, index: Int, currentTemperature: Temperature,
        temperature: Temperature,
    ): WeatherDailyItem = dailyItem.let {
        it.max = TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.max?.toDouble()).toInt()
        it.min = TemperatureMapper.convertDegreeTo(currentTemperature, temperature, it.min?.toDouble()).toInt()
    } as WeatherDailyItem


    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertToWeatherHourlyList(
        hourlyList: List<HourlyItem?>?, currentTemperature: Temperature,
        temperature: Temperature,
    ): List<WeatherHourlyItem> {
        val weatherHourlyList: MutableList<WeatherHourlyItem> = mutableListOf()
        hourlyList?.let {
            repeat(hourlyList.size) { index ->
                hourlyList[index]?.let { hourlyItem ->
                    weatherHourlyList.add(
                        convertToWeatherHourlyItem(
                            hourlyItem,
                            index,
                            currentTemperature,
                            temperature
                        )
                    )
                }
            }
        }
        return weatherHourlyList
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertWeatherToWeatherHourlyList(
        hourlyList: MutableList<WeatherHourlyItem?>, currentTemperature: Temperature,
        temperature: Temperature,
    ): List<WeatherHourlyItem> {
        hourlyList.let {
            repeat(hourlyList.size) { index ->
                hourlyList[index] = convertToWeatherHourlyItem(hourlyList[index]!!,index, currentTemperature, temperature)
            }
        }
        return hourlyList.toList() as List<WeatherHourlyItem>
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToWeatherDailyList(
        dailyList: List<DailyItem?>?, currentTemperature: Temperature,
        temperature: Temperature,
    ): List<WeatherDailyItem> {
        val weatherDailyList: MutableList<WeatherDailyItem> = mutableListOf()
        dailyList?.let {
            repeat(dailyList.size) { index ->
                dailyList[index]?.let { dailyItem ->
                    weatherDailyList.add(
                        convertToWeatherDailyItem(
                            dailyItem,
                            index,
                            currentTemperature,
                            temperature
                        )
                    )
                }
            }
        }
        return weatherDailyList
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun convertWeatherToWeatherDailyList(
        dailyList: MutableList<WeatherDailyItem?>, currentTemperature: Temperature,
        temperature: Temperature,
    ): List<WeatherDailyItem> {
        dailyList.let {
            repeat(dailyList.size) { index ->
                dailyList[index] = convertToWeatherDailyItem(dailyList[index]!!, index, currentTemperature, temperature)
            }
        }
        return dailyList.toList() as List<WeatherDailyItem>
    }


}