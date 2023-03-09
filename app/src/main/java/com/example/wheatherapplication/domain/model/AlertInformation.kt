package com.example.wheatherapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlertInformation(
    val senderName:String? = null,
    val event:String? = null,
    val description:String? = null,
): Parcelable
