package com.example.wheatherapplication.domain.usecase


import android.content.Context
import android.content.res.Configuration
import java.util.*
import javax.inject.Inject

class ChangeApplicationLanguage @Inject constructor(
    private val context: Context
) {
    operator fun invoke(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }
}