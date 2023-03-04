package com.example.wheatherapplication.ui.home


import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.HourForecastRowBinding
import com.example.wheatherapplication.domain.model.WeatherHourlyItem
import com.example.wheatherapplication.ui.common.BaseAdapter


class HourlyAdapter(
    private val hourList: List<WeatherHourlyItem>
) : BaseAdapter<HourForecastRowBinding, WeatherHourlyItem>(R.layout.hour_forecast_row, hourList) {
    override fun onBindViewHolder(holder: ViewHolder<HourForecastRowBinding>, position: Int) {
        holder.binding.hourItem = hourList[position]
    }
}