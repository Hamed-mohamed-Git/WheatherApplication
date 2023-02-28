package com.example.wheatherapplication.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel

abstract class BaseFragment<B:ViewDataBinding, V:ViewModel> : Fragment() {
    protected lateinit var binding:B
    protected abstract val viewModel:V

    @get:LayoutRes
    protected abstract val layoutRes :Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,layoutRes,container,false)
        binding.lifecycleOwner=this
        return binding.root
    }
}