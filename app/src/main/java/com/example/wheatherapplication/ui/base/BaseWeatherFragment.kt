package com.example.wheatherapplication.ui.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentBaseBinding
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class BaseWeatherFragment : BaseFragment<FragmentBaseBinding, BaseWeatherViewModel>() {
    override val viewModel: BaseWeatherViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_base
    private lateinit var favouriteWeatherViewPagerAdapter: FavouriteWeatherAdapter
    private var number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favouriteWeatherViewPagerAdapter = FavouriteWeatherAdapter(mutableListOf(),
            WeatherSetting()
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager2.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager2.offscreenPageLimit = 7
        binding.viewPager2.adapter = favouriteWeatherViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            if (position == 0)
                tab.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.navigation)
            else
                tab.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.bullet)
        }.attach()

        lifecycleScope.launch {
            delay(2000L)
            viewModel.favouriteWeathers.onEach {
                viewModel.settings.onEach {weatherSettings ->
                    if (favouriteWeatherViewPagerAdapter.favouriteWeathers.size == it.size || it.isEmpty())
                        viewModel.getFavouriteWeathers(weatherSettings)
                    favouriteWeatherViewPagerAdapter.changeRecyclerView(it,weatherSettings)
                }.launchIn(this)
            }.launchIn(this)
        }




        binding.favouriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_baseWeatherFragment_to_favouriteFragment)
        }

        binding.mapButton.setOnClickListener {
            findNavController().navigate(BaseWeatherFragmentDirections.actionBaseWeatherFragmentToLocationFragment(true))
        }
    }

}