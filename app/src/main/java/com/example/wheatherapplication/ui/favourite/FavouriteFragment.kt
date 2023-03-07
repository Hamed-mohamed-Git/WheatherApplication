package com.example.wheatherapplication.ui.favourite

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.databinding.FragmentFavouriteBinding
import com.example.wheatherapplication.domain.model.WeatherData
import com.example.wheatherapplication.domain.model.WeatherLatLng
import com.example.wheatherapplication.ui.common.BaseFragment
import com.example.wheatherapplication.ui.home.HomeFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding, FavouriteViewModel>(),
    FavouriteWeatherListener {
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
                        initializeWeatherFragment(latLng.latitude, latLng.longitude)
                        binding.addButton.visibility = View.VISIBLE
                        searchedWeatherAnimation(binding.searchedWeatherCardView, -1430f)
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
            }
        }
        binding.searchCardView.setOnClickListener {
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
            searchedWeatherAnimation(binding.searchedWeatherCardView, 1430f)
        }
        binding.addButton.setOnClickListener {
            deletedVisible = null
            viewModel.saveFavouriteWeather(homeFragment.weatherData)
            searchedWeatherAnimation(binding.searchedWeatherCardView, 1430f)
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

    private fun searchedWeatherAnimation(view: View, degree: Float) =
        ObjectAnimator.ofFloat(view, "translationY", degree).apply {
            duration = 2000
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
        initializeWeatherFragment(lat, lon)
        searchedWeatherAnimation(binding.searchedWeatherCardView, -1430f)
        binding.addButton.visibility = View.GONE
    }

    override fun onDeleteFavouriteWeatherClick(weatherData: WeatherData) {
        deletedWeather.add(weatherData)
        Toast.makeText(context, "hamed", Toast.LENGTH_LONG).show()
    }


}