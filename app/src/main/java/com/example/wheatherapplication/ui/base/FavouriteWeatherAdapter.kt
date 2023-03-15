package com.example.wheatherapplication.ui.base

import android.annotation.SuppressLint
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.BaseFavouriteHomeWeatherRowBinding
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherHourlyItem
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.ui.common.BaseAdapter
import com.example.wheatherapplication.ui.home.DailyAdapter
import com.example.wheatherapplication.ui.home.HourlyAdapter


class FavouriteWeatherAdapter(
    val favouriteWeathers: MutableList<WeatherData>,
    private var weatherSetting: WeatherSetting
) : BaseAdapter<BaseFavouriteHomeWeatherRowBinding, WeatherData>(
    R.layout.base_favourite_home_weather_row,
    favouriteWeathers
) {
    override fun onBindViewHolder(
        holder: ViewHolder<BaseFavouriteHomeWeatherRowBinding>,
        position: Int
    ) {
        with(holder.binding) {
            favouriteWeathers[position].let {
                settings = weatherSetting
                weatherData = it
                hourlyAdapter = HourlyAdapter(it.hourly as List<WeatherHourlyItem>)
                dailyAdapter = DailyAdapter(it.daily as List<WeatherDailyItem>)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeRecyclerView(weathers: List<WeatherData>,settings: WeatherSetting) {
        weatherSetting = settings
        favouriteWeathers.clear()
        repeat(weathers.size) {
            favouriteWeathers.add(weathers[it])
        }
        notifyDataSetChanged()
    }
}