package com.example.wheatherapplication.ui.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentLocationBinding
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding,LocationViewModel>(), OnMapReadyCallback, LocationListener {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var placeFragment: AutocompleteSupportFragment
    private var latLng: LatLng? = null
    override val viewModel: LocationViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_location

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                // Precise location access granted.
                viewModel.getGpsLocation(checkPermission())
            }
            else -> {
                // No location access granted.
                Toast.makeText(
                    context,
                    "you can use another way to get your location using map",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                data?.let {
                    Autocomplete.getPlaceFromIntent(it).latLng?.let {
                        viewModel.latLng.postValue(Autocomplete.getPlaceFromIntent(data).latLng)
                    }
                }
            }
        }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.locationAction = this
        binding.viewModel = viewModel
        initializePlaces()
        binding.setupButton.setOnClickListener {
            latLng?.let {
                viewModel.saveLatLng(it)
            }
        }
        mapFragment = binding.map.getFragment()
        placeFragment = binding.autocompleteFragment.getFragment()
        mapFragment.getMapAsync(this)
        viewModel.locationEvent.observe(requireActivity()) {
            when (it) {
                is LocationEvent.LocationPermissionRequest -> {
                    requestPermissions()
                }
                else -> {}
            }
        }
    }

    private fun checkPermission(): Boolean = (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED)


    private fun requestPermissions() = locationPermissionRequest.launch(
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.latLng.observe(this) {
            it?.apply {
                viewModel.convertLatLngToAddress(it)
                this@LocationFragment.latLng = this
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(it))
                googleMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition(
                            this,
                            15.5f,
                            0f,
                            0f
                        )
                    )
                )
                googleMap.moveCamera(CameraUpdateFactory.scrollBy(0f, 300f))

            }
        }
        googleMap.setOnMapLongClickListener {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it))
            googleMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        it,
                        15.5f,
                        0f,
                        0f
                    )
                )
            )
            viewModel.convertLatLngToAddress(it)
        }

    }

    private fun initializePlaces() {
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            context?.applicationContext?.let {
                Places.initialize(it, apiKey)
                Places.createClient(it)
            }
        }
    }


    override fun onGpsLocationListener() {
        binding.autocompleteFragment.visibility = View.GONE
        viewModel.getGpsLocation(checkPermission())
    }

    override fun onGoogleMapLocationListener() {
        resultLauncher.launch(
            Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, listOf(
                    Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG
                )
            ).build(requireContext())
        )
    }

}