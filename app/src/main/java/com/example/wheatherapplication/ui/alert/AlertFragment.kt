package com.example.wheatherapplication.ui.alert

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.data.map.TimeMapper
import com.example.wheatherapplication.databinding.FragmentAlertBinding
import com.example.wheatherapplication.domain.model.WeatherSetting
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class AlertFragment : BaseFragment<FragmentAlertBinding, AlertViewModel>() {
    override val viewModel: AlertViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_alert
    private val calender: Calendar by lazy {
        Calendar.getInstance()
    }
    private val endAlertCalender: Calendar by lazy {
        Calendar.getInstance()
    }
    private var enqueuedCalender: Calendar? = null
    private lateinit var date: List<String>



    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dateTextView.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setCalendarConstraints(
                        CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
                            .build()
                    ).setTheme(R.style.AppTheme_DialogTheme)
                    .build()
            datePicker.show(parentFragmentManager, "DATE_PICKER_RANGE")
            datePicker.addOnPositiveButtonClickListener {
                if (enqueuedCalender == null)
                    enqueuedCalender = Calendar.getInstance()
                enqueuedCalender?.time = TimeMapper.convertTimeStamp(it / 1000)
                calender.time = TimeMapper.convertTimeStamp(it / 1000)
                date = calender.time.toString().split(" ").toList()
                binding.dateTextView.text = "${date[0]}, ${date[1]} ${date[2]}, ${date[5]}"
            }
        }
        binding.hourTextView.setOnClickListener {
            val timePicker = MaterialTimePicker
                .Builder()
                .setInputMode(INPUT_MODE_CLOCK)
                .setTitleText("Select a Clock")
                .build()
            timePicker.show(parentFragmentManager, "TIME_PICKER")
            timePicker.addOnPositiveButtonClickListener {
                if (enqueuedCalender == null)
                    enqueuedCalender = Calendar.getInstance()
                enqueuedCalender?.set(Calendar.HOUR, timePicker.hour)
                enqueuedCalender?.set(Calendar.MINUTE, timePicker.minute)
                binding.hourTextView.text =
                    "${timePicker.hour}:${if (timePicker.minute == 0) "00" else timePicker.minute}"
                binding.hourClock.text = if (timePicker.hour <= 12) "AM" else "PM"
            }
        }
        lifecycleScope.launch {
            viewModel.setting.collect {

                binding.severeSwitch.isChecked = it.severeWeather ?: false
                binding.durationTimeSwitch.isChecked = it.durationTime ?: false
                binding.severeSwitch.setOnCheckedChangeListener { _, checked ->

                    if (it.language != null)
                        viewModel.setSettings(
                            WeatherSetting(
                                checked,
                                if (!checked) false else it.durationTime,
                                it.notificationPermission,
                                it.locationType,
                                it.temperatureUnit,
                                it.lengthUnit,
                                it.language,
                            )
                        )
                    if (!checked){
                        viewModel.saveTime(0L,0L)
                        viewModel.cancelNotification()
                    }

                }
                binding.durationTimeSwitch.setOnCheckedChangeListener { _, checked ->
                    if (binding.severeSwitch.isChecked) {
                        if (it.language != null){
                            viewModel.setSettings(
                                WeatherSetting(
                                    it.severeWeather,
                                    checked,
                                    it.notificationPermission,
                                    it.locationType,
                                    it.temperatureUnit,
                                    it.lengthUnit,
                                    it.language,
                                )
                            )
                        }
                        if (!checked){
                            binding.enqueueAlarm.isChecked = false
                            viewModel.saveTime(0L,0L)
                            viewModel.cancelNotification()
                        }

                    } else if (!binding.severeSwitch.isChecked && checked) {
                        binding.durationTimeSwitch.isChecked = false
                        Toast.makeText(
                            context,
                            "You should open severe Weather before!",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                    if (binding.durationTimeSwitch.isChecked) {
                        searchedSettingsAnimation(binding.timeCardView, 0f, 1f)
                        searchedSettingsAnimation(binding.textView29, 0f, 1f)
                        searchedSettingsAnimation(binding.textView30, 0f, 1f)
                    } else {
                        searchedSettingsAnimation(binding.timeCardView, 1f, 0f)
                        searchedSettingsAnimation(binding.textView29, 1f, 0f)
                        searchedSettingsAnimation(binding.textView30, 1f, 0f)
                    }

                }


            }
        }
        lifecycleScope.launch {
            viewModel.weatherInfo.collect{
                it.alertEnd?.let {alertEnd ->
                    if (alertEnd != 0L){
                        binding.enqueueAlarm.isChecked = true
                        endAlertCalender.time = TimeMapper.convertTimeStamp(alertEnd / 1000)
                        val anotherDate = endAlertCalender.time.toString().split(" ").toList()
                        binding.dateTextView.text = "${anotherDate[0]}, ${anotherDate[1]} ${anotherDate[2]}, ${anotherDate[5]}"
                        endAlertCalender.get(Calendar.MINUTE).let {minute ->
                            endAlertCalender.get(Calendar.HOUR_OF_DAY).let {hour ->
                                binding.hourTextView.text =
                                    "${hour}:${if (minute== 0) "00" else minute}"
                                binding.hourClock.text = if (hour <= 12) "AM" else "PM"
                            }
                        }


                    }
                }

                binding.enqueueAlarm.setOnCheckedChangeListener { _, isChecked ->
                    if (enqueuedCalender == null && it.alertEnd == 0L){
                        Toast.makeText(context,"you should set a specific time and date!",Toast.LENGTH_SHORT).show()
                        binding.enqueueAlarm.isChecked = false
                    }else{
                        if (isChecked) {
                            enqueuedCalender?.let {calender->
                                viewModel.saveTime(System.currentTimeMillis(), calender.timeInMillis)
                                viewModel.enqueueNotification()
                            }
                        } else if (it.alertEnd != 0L)
                            viewModel.cancelNotification()
                    }

                }

            }
        }


    }

    private fun searchedSettingsAnimation(view: View, alphaIn: Float, alphaOut: Float) =
        ObjectAnimator.ofFloat(view, "alpha", alphaIn, alphaOut).apply {
            duration = 700
            start()
        }


}