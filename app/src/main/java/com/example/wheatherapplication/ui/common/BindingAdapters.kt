package com.example.wheatherapplication.ui.common

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("imageUrl")
@NonNull
fun loadImage(imageView: ImageView, url: String?){
    imageView.load(url)
}