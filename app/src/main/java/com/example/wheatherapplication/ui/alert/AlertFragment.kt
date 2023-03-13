package com.example.wheatherapplication.ui.alert

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wheatherapplication.R
import com.example.wheatherapplication.data.map.TimeMapper
import com.example.wheatherapplication.databinding.FragmentAlertBinding
import com.example.wheatherapplication.ui.common.BaseFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AlertFragment : BaseFragment<FragmentAlertBinding, AlertViewModel>() {
    override val viewModel: AlertViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_alert
    private val calender: Calendar by lazy {
        Calendar.getInstance()
    }
    private var enqueuedCalender:Calendar? = null
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
                enqueuedCalender?.set(Calendar.HOUR,timePicker.hour)
                enqueuedCalender?.set(Calendar.MINUTE,timePicker.minute)
                binding.hourTextView.text = "${timePicker.hour}:${if(timePicker.minute == 0)  "00" else timePicker.minute }"
                binding.hourClock.text = if (timePicker.hour <= 12) "AM" else "PM"
            }
        }
        binding.enqueueAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                enqueuedCalender?.let {
                    viewModel.saveTime(System.currentTimeMillis(),it.timeInMillis)
                    viewModel.enqueueNotification()
                }
            }else
                viewModel.cancelNotification()

        }
    }

}