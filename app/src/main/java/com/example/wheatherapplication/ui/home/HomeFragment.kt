package com.example.wheatherapplication.ui.home

import androidx.fragment.app.viewModels
import com.example.wheatherapplication.R
import com.example.wheatherapplication.databinding.FragmentHomeBinding
import com.example.wheatherapplication.ui.common.BaseFragment
import com.example.wheatherapplication.ui.location.LocationViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding,LocationViewModel>() {
    override val viewModel: LocationViewModel by viewModels()
    override val layoutRes: Int = R.layout.fragment_home


}