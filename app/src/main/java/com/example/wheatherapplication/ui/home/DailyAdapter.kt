package com.example.wheatherapplication.ui.home

import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.DailyForcastRowBinding
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.ui.common.BaseAdapter

class DailyAdapter(
    private val dailyList: List<WeatherDailyItem>
) : BaseAdapter<DailyForcastRowBinding, WeatherDailyItem>(R.layout.daily_forcast_row, dailyList) {
    override fun onBindViewHolder(holder: ViewHolder<DailyForcastRowBinding>, position: Int) {
        holder.binding.dailyItem = dailyList[position]
    }
}