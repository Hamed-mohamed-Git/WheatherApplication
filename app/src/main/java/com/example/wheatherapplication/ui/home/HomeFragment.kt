package com.example.wheatherapplication.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentHomeBinding
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherHourlyItem
import com.example.wheatherapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_home
    lateinit var weatherData: WeatherData


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        with(arguments?.getDouble("lat")) {
            if (arguments?.getBoolean("flag") as Boolean) {
                viewModel.getFavouriteWeather(this ?: 0.0)
            } else
                viewModel.getWeather(this ?: 0.0, arguments?.getDouble("lon") ?: 0.0)
        }


        lifecycleScope.launchWhenResumed {
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
        lifecycleScope.launch {
            viewModel.weatherSetting.collect{
                binding.settings = it
            }
        }


    }


}