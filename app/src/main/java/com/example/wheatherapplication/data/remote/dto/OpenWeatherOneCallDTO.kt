package com.example.wheatherapplication.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenWeatherOneCallDTO(

    @field:SerializedName("current")
	val current: Current? = null,

    @field:SerializedName("minutely")
	val minutely: List<MinutelyItem?>? = null,

    @field:SerializedName("timezone")
	val timezone: String? = null,

    @field:SerializedName("timezone_offset")
	val timezoneOffset: Int? = null,

    @field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

    @field:SerializedName("lon")
	val lon: Double? = null,

    @field:SerializedName("hourly")
	val hourly: List<HourlyItem?>? = null,

    @field:SerializedName("lat")
	val lat: Double? = null,

    @field:SerializedName("alerts")
	val alerts: List<AlertsItem?>? = null
)

data class HourlyItem(

    @field:SerializedName("temp")
	val temp: Double? = null,

    @field:SerializedName("visibility")
	val visibility: Int? = null,

    @field:SerializedName("uvi")
	val uvi: Double? = null,

    @field:SerializedName("pressure")
	val pressure: Int? = null,

    @field:SerializedName("clouds")
	val clouds: Int? = null,

    @field:SerializedName("feels_like")
	val feelsLike: Any? = null,

    @field:SerializedName("wind_gust")
	val windGust: Any? = null,

    @field:SerializedName("dt")
	val dt: Int? = null,

    @field:SerializedName("pop")
	val pop: Any? = null,

    @field:SerializedName("wind_deg")
	val windDeg: Int? = null,

    @field:SerializedName("dew_point")
	val dewPoint: Any? = null,

    @field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
	val humidity: Int? = null,

    @field:SerializedName("wind_speed")
	val windSpeed: Any? = null,

    @field:SerializedName("rain")
	val rain: Rain? = null
)

data class Rain(

	@field:SerializedName("1h")
	val jsonMember1h: Any? = null
)

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class FeelsLike(

	@field:SerializedName("eve")
	val eve: Any? = null,

	@field:SerializedName("night")
	val night: Any? = null,

	@field:SerializedName("day")
	val day: Any? = null,

	@field:SerializedName("morn")
	val morn: Any? = null
)

data class DailyItem(

    @field:SerializedName("moonset")
	val moonset: Int? = null,

    @field:SerializedName("sunrise")
	val sunrise: Int? = null,

    @field:SerializedName("temp")
	val temp: Temp? = null,

    @field:SerializedName("moon_phase")
	val moonPhase: Any? = null,

    @field:SerializedName("uvi")
	val uvi: Double? = null,

    @field:SerializedName("moonrise")
	val moonrise: Int? = null,

    @field:SerializedName("pressure")
	val pressure: Int? = null,

    @field:SerializedName("clouds")
	val clouds: Int? = null,

    @field:SerializedName("feels_like")
	val feelsLike: FeelsLike? = null,

    @field:SerializedName("wind_gust")
	val windGust: Any? = null,

    @field:SerializedName("dt")
	val dt: Int? = null,

    @field:SerializedName("pop")
	val pop: Any? = null,

    @field:SerializedName("wind_deg")
	val windDeg: Int? = null,

    @field:SerializedName("dew_point")
	val dewPoint: Any? = null,

    @field:SerializedName("sunset")
	val sunset: Int? = null,

    @field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
	val humidity: Int? = null,

    @field:SerializedName("wind_speed")
	val windSpeed: Any? = null,

    @field:SerializedName("rain")
	val rain: Any? = null
)

data class Current(

    @field:SerializedName("sunrise")
	val sunrise: Int? = null,

    @field:SerializedName("temp")
	val temp: Double? = null,

    @field:SerializedName("visibility")
	val visibility: Int? = null,

    @field:SerializedName("uvi")
	val uvi: Double? = null,

    @field:SerializedName("pressure")
	val pressure: Int? = null,

    @field:SerializedName("clouds")
	val clouds: Int? = null,

    @field:SerializedName("feels_like")
	val feelsLike: Any? = null,

    @field:SerializedName("wind_gust")
	val windGust: Any? = null,

    @field:SerializedName("dt")
	val dt: Int? = null,

    @field:SerializedName("wind_deg")
	val windDeg: Int? = null,

    @field:SerializedName("dew_point")
	val dewPoint: Double? = null,

    @field:SerializedName("sunset")
	val sunset: Int? = null,

    @field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
	val humidity: Int? = null,

    @field:SerializedName("wind_speed")
	val windSpeed: Any? = null
)

data class Temp(

	@field:SerializedName("min")
	val min: Double? = null,

	@field:SerializedName("max")
	val max: Double? = null,

	@field:SerializedName("eve")
	val eve: Any? = null,

	@field:SerializedName("night")
	val night: Any? = null,

	@field:SerializedName("day")
	val day: Any? = null,

	@field:SerializedName("morn")
	val morn: Any? = null
)

data class AlertsItem(

	@field:SerializedName("start")
	val start: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("sender_name")
	val senderName: String? = null,

	@field:SerializedName("end")
	val end: Int? = null,

	@field:SerializedName("event")
	val event: String? = null,

	@field:SerializedName("tags")
	val tags: List<Any?>? = null
)



data class MinutelyItem(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("precipitation")
	val precipitation: Any? = null
)

