package com.example.wheatherapplication.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentHomeBinding
import com.example.wheatherapplication.domain.model.WeatherDailyItem
import com.example.wheatherapplication.domain.model.WeatherHourlyItem
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment(
    private val latLng: LatLng
) : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.getWeatherInfo(latLng)
        lifecycleScope.launch {
            viewModel.weatherInfo.collect {
                binding.weatherData = it
                it.hourly?.let { hourlyList ->
                    viewModel.saveWeather(it)
                    binding.hourlyAdapter = HourlyAdapter(hourlyList as List<WeatherHourlyItem>)
                }
                it.daily?.let { dailyList ->
                    binding.dailyAdapter = DailyAdapter(dailyList as List<WeatherDailyItem>)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.locationInfo.collect { address ->
                binding.address = address
            }
        }
    }


}