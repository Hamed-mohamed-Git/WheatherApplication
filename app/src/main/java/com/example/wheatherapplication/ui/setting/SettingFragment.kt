package com.example.wheatherapplication.ui.setting


import android.os.Bundle
import android.view.View
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

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingsViewModel>() {
    override val viewModel: SettingsViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_setting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSettings()
        initView()
        lifecycleScope.launchWhenResumed {
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
                    LengthUnit.MILE -> {
                        binding.MileSwitch.isChecked = true
                    }
                    LengthUnit.KILOMETER -> {
                        binding.KiloSwitch.isChecked = true
                    }
                    else -> {}
                }
                binding.celSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.fahrenheitSwitch.isChecked = false
                    binding.kelvinSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            Temperature.CELSIUS,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }
                binding.kelvinSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.fahrenheitSwitch.isChecked = false
                    binding.celSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            Temperature.KELVIN,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }

                binding.fahrenheitSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.celSwitch.isChecked = false
                    binding.kelvinSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            Temperature.FAHRENHEIT,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }
                binding.KiloSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.MileSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            it.temperatureUnit,
                            LengthUnit.KILOMETER,
                            it.language
                        )
                    )
                }
                binding.MileSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.KiloSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            it.temperatureUnit,
                            LengthUnit.MILE,
                            it.language
                        )
                    )
                }
                binding.arabicSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.englishSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            it.temperatureUnit,
                            it.lengthUnit,
                            Language.ARABIC
                        )
                    )
                }
                binding.englishSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.arabicSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            it.locationType,
                            it.temperatureUnit,
                            it.lengthUnit,
                            Language.ENGLISH
                        )
                    )
                }
                binding.googleMapSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.locationSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
                            LocationType.GOOGLE_MAP,
                            it.temperatureUnit,
                            it.lengthUnit,
                            it.language
                        )
                    )
                }

                binding.locationSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    binding.googleMapSwitch.isChecked = false
                    viewModel.setSettings(
                        WeatherSetting(
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
}