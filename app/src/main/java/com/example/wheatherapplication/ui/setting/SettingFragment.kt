package com.example.wheatherapplication.ui.setting


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentSettingBinding
import com.example.wheatherapplication.ui.base.BaseWeatherViewModel
import com.example.wheatherapplication.ui.common.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding,BaseWeatherViewModel>(){
    override val viewModel: BaseWeatherViewModel by viewModels()
    override val layoutRes:Int = R.layout.fragment_setting

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}