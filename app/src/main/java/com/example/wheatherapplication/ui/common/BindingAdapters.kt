package com.example.wheatherapplication.ui.common

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import coil.load
import com.example.wheatherapplication.constants.LengthUnit

@BindingAdapter("imageUrl")
@NonNull
fun loadImage(imageView: ImageView, url: String?){
    imageView.load(url)
}


@BindingAdapter("titleText")
@NonNull
fun fetchYourTitleText(view: TextView, type: LengthUnit?) {
    view.apply {
        text = when (type) {
            LengthUnit.KM -> "km/h"
            LengthUnit.MI -> "mile/h"
            else -> "--"
        }
    }
}