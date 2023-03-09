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
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BaseWeatherFragment : BaseFragment<FragmentBaseBinding, BaseWeatherViewModel>() {
    override val viewModel: BaseWeatherViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_base


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavouriteWeathers()
        viewModel.getSettings()
        lifecycleScope.launchWhenResumed {
            viewModel.favouriteWeathers.collect {
                if (it.isNotEmpty()) {
                    binding.viewPager2.adapter = FavouriteWeatherViewPagerAdapter(
                        it,
                        parentFragmentManager,
                        lifecycle
                    )
                    TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
                        if (position == 0)
                            tab.icon =
                                ContextCompat.getDrawable(requireContext(), R.drawable.navigation)
                        else
                            tab.icon =
                                ContextCompat.getDrawable(requireContext(), R.drawable.bullet)
                    }.attach()
                }

            }
        }

        binding.favouriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_baseWeatherFragment_to_favouriteFragment)
        }

        binding.mapButton.setOnClickListener {
            // Set the alarm to start at 8:30 a.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 20)
                set(Calendar.MINUTE, 39)
            }
            viewModel.setAlarmTime(calendar)
        }
    }

}