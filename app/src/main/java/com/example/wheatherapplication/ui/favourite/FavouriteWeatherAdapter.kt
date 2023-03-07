package com.example.wheatherapplication.ui.favourite

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FavouriteWeatherRowBinding
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.ui.common.BaseAdapter

class FavouriteWeatherAdapter(
    private val favouriteWeatherList: MutableList<WeatherData>,
    private val favouriteWeatherListener: FavouriteWeatherListener
) :
    BaseAdapter<FavouriteWeatherRowBinding, WeatherData>(
        R.layout.favourite_weather_row,
        favouriteWeatherList
    ) {
    private val holders:MutableList<ViewHolder<FavouriteWeatherRowBinding>> = mutableListOf()
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder<FavouriteWeatherRowBinding>, position: Int) {
        holders.add(holder)
        holder.binding.also {
            it.weatherData = favouriteWeatherList[position].also { weatherData ->
                it.deleteFavouriteIcon.setOnClickListener {
                    favouriteWeatherList.removeAt(position)
                    favouriteWeatherListener.onDeleteFavouriteWeatherClick(weatherData)
                    notifyDataSetChanged()
                }
            }
            it.favouriteWeatherListener = favouriteWeatherListener
        }
    }
    fun changeDeleteVisible(visible:Boolean){
        repeat(holders.size){index->
            holders[index].binding.let {
                if (visible) {
                    it.card.isEnabled = false
                    weatherAnimation(it.deleteFavouriteIcon, 70f)
                    weatherAnimation(it.locationTextView, 70f)
                    weatherAnimation(it.hourTextView, 70f)
                    weatherAnimation(it.textView17, 70f)
                } else {
                    it.card.isEnabled = true
                    weatherAnimation(it.deleteFavouriteIcon, -5f)
                    weatherAnimation(it.locationTextView, -5f)
                    weatherAnimation(it.hourTextView, -5f)
                    weatherAnimation(it.textView17, -5f)
                }
            }
        }
    }

    private fun weatherAnimation(view: View, degree: Float) =
        ObjectAnimator.ofFloat(view, "translationX", degree).apply {
            duration = 1500
            start()
        }
}