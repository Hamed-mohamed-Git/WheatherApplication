package com.example.wheatherapplication.ui.main_Activity

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wheatherapplication.R
import com.example.wheatherapplication.constants.Language
import com.example.wheatherapplication.ui.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var language:String = "Ar"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getSetting()
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.setting.collect {
                it.language?.let {language ->
                    if (language == Language.ARABIC)
                        LocaleHelper.updateResourcesLegacy(this@MainActivity,"Ar")
                }
            }
        }



    }

}