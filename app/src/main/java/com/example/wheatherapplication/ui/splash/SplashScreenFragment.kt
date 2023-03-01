package com.example.wheatherapplication.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentSplashScreenBinding
import com.example.wheatherapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenFragment : BaseFragment<FragmentSplashScreenBinding,SplashScreenViewModel>() {
    override val viewModel: SplashScreenViewModel by viewModels()
    override val layoutRes: Int =R.layout.fragment_splash_screen

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDataStoreLocationDataFound()
        lifecycleScope.launch {
            viewModel.dataStoreLocationData.collect{
                when(it){
                    is DataStoreLocationState.LocationFounded -> {
                        delay(1500)
                        findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
                    }
                    is DataStoreLocationState.LocationNotFounded -> {
                        delay(1500)
                        findNavController().navigate(R.id.action_splashScreenFragment_to_locationFragment)
                    }
                }
            }
        }
    }

}