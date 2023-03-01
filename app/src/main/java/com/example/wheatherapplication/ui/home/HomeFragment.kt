package com.example.wheatherapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentHomeBinding
import com.example.wheatherapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.getLocationWeather()
        lifecycleScope.launch {
            viewModel.weatherInfo.collect{
                Log.i("hamed", "onViewCreated: ${it.timezone}")
            }
        }
    }


}