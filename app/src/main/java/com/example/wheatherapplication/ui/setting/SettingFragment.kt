package com.example.wheatherapplication.ui.setting


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.constants.Language
import com.example.wheatherapplication.constants.LengthUnit
import com.example.wheatherapplication.constants.LocationType
import com.example.wheatherapplication.constants.Temperature
import com.example.wheatherapplication.databinding.FragmentSettingBinding
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingsViewModel>() {
    override val viewModel: SettingsViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_setting
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSettings()
        initView()
        lifecycleScope.launch {
            viewModel.setting.collect {
                when (it.temperatureUnit) {
                    Temperature.CELSIUS -> {
                        binding.celSwitch.isChecked = true
                    }
                    Temperature.FAHRENHEIT -> {
                        binding.fahrenheitSwitch.isChecked = true
                    }
                    Temperature.KELVIN -> {
                        binding.kelvinSwitch.isChecked = true
                    }
                    else -> {}
                }
                when (it.language) {
                    Language.ARABIC -> {
                        binding.arabicSwitch.isChecked = true
                    }
                    Language.ENGLISH -> {
                        binding.englishSwitch.isChecked = true
                    }
                    else -> {}
                }

                when (it.locationType) {
                    LocationType.GPS -> {
                        binding.locationSwitch.isChecked = true
                    }
                    LocationType.GOOGLE_MAP -> {
                        binding.googleMapSwitch.isChecked = true
                    }
                    else -> {}
                }

                when (it.lengthUnit) {
                    LengthUnit.MI -> {
                        binding.MileSwitch.isChecked = true
                    }
                    LengthUnit.KM -> {
                        binding.KiloSwitch.isChecked = true
                    }
                    else -> {}
                }
                binding.celSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.fahrenheitSwitch.isChecked = false
                    binding.kelvinSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            it.locationType,
                            Temperature.CELSIUS,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }
                binding.kelvinSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.fahrenheitSwitch.isChecked = false
                    binding.celSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            it.locationType,
                            Temperature.KELVIN,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }

                binding.fahrenheitSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.celSwitch.isChecked = false
                    binding.kelvinSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            it.locationType,
                            Temperature.FAHRENHEIT,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }
                binding.KiloSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.MileSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            it.locationType,
                            it.temperatureUnit,
                            LengthUnit.KM,
                            it.language
                        )
                    )
                }
                binding.MileSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.KiloSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            it.locationType,
                            it.temperatureUnit,
                            LengthUnit.MI,
                            it.language
                        )
                    )
                }
                binding.arabicSwitch.setOnCheckedChangeListener { _, checked ->
                    if (checked&& it.language == Language.ENGLISH){
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("Ar"))
                        binding.englishSwitch.isChecked = false
                        viewModel.setSettings(
                            WeatherSetting(
                                it.severeWeather,
                                it.durationTime,
                                it.notificationPermission,
                                it.locationType,
                                it.temperatureUnit,
                                it.lengthUnit,
                                Language.ARABIC
                            )
                        )
                    }


                }
                binding.englishSwitch.setOnCheckedChangeListener { _, checked ->
                    if (checked && it.language == Language.ARABIC){
                        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("En"))
                        binding.arabicSwitch.isChecked = false
                        viewModel.setSettings(
                            WeatherSetting(
                                it.severeWeather,
                                it.durationTime,
                                it.notificationPermission,
                                it.locationType,
                                it.temperatureUnit,
                                it.lengthUnit,
                                Language.ENGLISH
                            )
                        )
                    }


                }
                binding.googleMapSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.locationSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            LocationType.GOOGLE_MAP,
                            it.temperatureUnit,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }

                binding.locationSwitch.setOnCheckedChangeListener { _, _ ->
                    binding.googleMapSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.severeWeather,
                            it.durationTime,
                            it.notificationPermission,
                            LocationType.GPS,
                            it.temperatureUnit,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }
            }
        }
    }

    private fun initView() {
        binding.celSwitch.isChecked = false
        binding.fahrenheitSwitch.isChecked = false
        binding.kelvinSwitch.isChecked = false
        binding.arabicSwitch.isChecked = false
        binding.englishSwitch.isChecked = false
        binding.locationSwitch.isChecked = false
        binding.googleMapSwitch.isChecked = false
        binding.KiloSwitch.isChecked = false
        binding.MileSwitch.isChecked = false
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
}