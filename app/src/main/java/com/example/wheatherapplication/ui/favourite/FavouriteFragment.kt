package com.example.wheatherapplication.ui.favourite

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.wheatherapplication.R
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.databinding.FragmentFavouriteBinding
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherLatLng
import com.example.wheatherapplication.ui.common.BaseFragment
import com.example.wheatherapplication.ui.common.DeleteConfirmationFragmentDialog
import com.example.wheatherapplication.ui.common.DeleteFragmentConfirmationListener
import com.example.wheatherapplication.ui.home.HomeFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>(),
    FavouriteWeatherListener, DeleteFragmentConfirmationListener {
    override val viewModel: FavouriteViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_favourite
    private lateinit var homeFragment: HomeFragment
    private val deletedWeather: MutableList<WeatherData> = mutableListOf()
    private var deletedVisible: Boolean? = null
    private lateinit var favouriteWeatherAdapter: FavouriteWeatherAdapter
    private val searchResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data?.let {
                    Autocomplete.getPlaceFromIntent(it).latLng?.let { latLng ->
                        searchedWeatherAnimation(
                            binding.searchedWeatherCardView,
                            -1430f,
                            "translationY"
                        ).doOnEnd {
                            initializeWeatherFragment(latLng.latitude, latLng.longitude)
                            binding.addButton.visibility = View.VISIBLE
                        }

                    }
                }
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getFavouriteWeathers()
            viewModel.favouriteWeathers.collect { weatherDataList ->
                if (deletedVisible == null) {
                    favouriteWeatherAdapter = FavouriteWeatherAdapter(
                        weatherDataList.toMutableList(),
                        this@FavouriteFragment
                    )
                    binding.adapter = favouriteWeatherAdapter
                }
                binding.moreOptionsImageView.setOnClickListener {
                    with(binding.settingsCardView) {
                        if (alpha == 0f) {
                            this.visibility = View.VISIBLE
                            searchedSettingsAnimation(this, 0f, 1f)
                        } else {
                            searchedSettingsAnimation(this, 1f, 0f).doOnEnd {
                                this.visibility = View.GONE
                            }

                        }

                    }


                }
                binding.settingsButton.setOnClickListener {
                    findNavController().navigate(R.id.action_favouriteFragment_to_settingFragment)
                }
                binding.textView12.setOnClickListener {
                    searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                        binding.settingsCardView.visibility = View.GONE
                    }
                }
                binding.nestedScrollViewLayout.setOnClickListener {
                    searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                        binding.settingsCardView.visibility = View.GONE
                    }
                }
                binding.editListButton.setOnClickListener {
                    searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                        binding.settingsCardView.visibility = View.GONE
                    }
                    binding.doneButton.visibility = View.VISIBLE
                    deletedVisible = true
                    favouriteWeatherAdapter.changeDeleteVisible(deletedVisible!!)
                }
                binding.doneButton.setOnClickListener {
                    deletedVisible = false
                    favouriteWeatherAdapter.changeDeleteVisible(deletedVisible!!)
                    viewModel.deleteFavouriteWeather(deletedWeather)
                    it.visibility = View.GONE
                }
                binding.reportButton.setOnClickListener {
                    searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                        Toast.makeText(
                            context,
                            "This Service is not Available now!",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.settingsCardView.visibility = View.GONE
                    }
                }
                binding.frameLayout4.setOnClickListener {
                    searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                        binding.settingsCardView.visibility = View.GONE
                    }
                }
            }
        }
        binding.searchCardView.setOnClickListener {
            searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
                binding.settingsCardView.visibility = View.GONE
            }
            initializePlaces()
            searchResultLauncher.launch(
                Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY, listOf(
                        Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG
                    )
                ).build(requireContext())
            )
        }
        binding.cancelButton.setOnClickListener {
            searchedWeatherAnimation(binding.searchedWeatherCardView, 1430f, "translationY")
        }
        binding.addButton.setOnClickListener {
            deletedVisible = null
            viewModel.saveFavouriteWeather(homeFragment.weatherData)
            searchedWeatherAnimation(binding.searchedWeatherCardView, 1430f, "translationY")
            Toast.makeText(context, "The location saved successfully", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializePlaces() {
        val apiKey = Constants.PLACES_API_KEY
        if (!Places.isInitialized()) {
            context?.applicationContext?.let {
                Places.initialize(it, apiKey)
                Places.createClient(it)
            }
        }
    }

    private fun searchedWeatherAnimation(view: View, degree: Float, animationType: String) =
        ObjectAnimator.ofFloat(view, animationType, degree).apply {
            duration = 2000
            start()
        }

    private fun searchedSettingsAnimation(view: View, alphaIn: Float, alphaOut: Float) =
        ObjectAnimator.ofFloat(view, "alpha", alphaIn, alphaOut).apply {
            duration = 700
            start()
        }

    private fun initializeWeatherFragment(lat: Double?, lon: Double?) {
        homeFragment = HomeFragment().apply {
            arguments = bundleOf("lat" to WeatherLatLng(lat, lon))
        }
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(binding.searchedWeather.id, homeFragment).commit()
    }

    override fun onFavouriteWeatherClick(lat: Double?, lon: Double?) {
        searchedSettingsAnimation(binding.settingsCardView, 1f, 0f).doOnEnd {
            binding.settingsCardView.visibility = View.GONE
        }
        searchedWeatherAnimation(binding.searchedWeatherCardView, -1430f, "translationY").doOnEnd {
            initializeWeatherFragment(lat, lon)
            binding.addButton.visibility = View.GONE
        }
    }

    override fun onDeleteFavouriteWeatherClick(weatherData: WeatherData) {
        deletedWeather.add(weatherData)
        Toast.makeText(context, "hamed", Toast.LENGTH_LONG).show()
    }

    override fun onConfirmationDelete() {
        Toast.makeText(context, "hamed", Toast.LENGTH_LONG).show()
    }


}