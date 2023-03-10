package com.example.wheatherapplication.ui.base

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherLatLng
import com.example.wheatherapplication.ui.home.HomeFragment
import com.google.android.gms.maps.model.LatLng

class FavouriteWeatherViewPagerAdapter(
    private val favouriteWeatherList: List<WeatherData>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = favouriteWeatherList.size

    override fun createFragment(position: Int): Fragment = HomeFragment(
    ).apply {
        arguments = bundleOf(
            "lat" to (favouriteWeatherList[position].lat ?: 0.0),
            "lon" to (favouriteWeatherList[position].lon ?: 0.0),
            "flag" to true
        )
    }
}