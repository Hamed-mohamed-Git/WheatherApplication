package com.example.wheatherapplication.ui.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentHomeBinding
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherHourlyItem
import com.example.wheatherapplication.domain.model.WeatherLatLng
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_home
    lateinit var weatherData:WeatherData

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(arguments?.getSerializable("lat") as WeatherLatLng){
            viewModel.getWeather(LatLng(this.lat ?: 0.0,this.lon ?: 0.0))
        }
        lifecycleScope.launch {
            viewModel.weatherInfo.collect {
                binding.weatherData = it
                weatherData = it
                binding.address = it.address
                it.hourly?.let { hourlyList ->
                    binding.hourlyAdapter = HourlyAdapter(hourlyList as List<WeatherHourlyItem>)
                }
                it.daily?.let { dailyList ->
                    binding.dailyAdapter = DailyAdapter(dailyList as List<WeatherDailyItem>)
                }
            }

        }


    }


}