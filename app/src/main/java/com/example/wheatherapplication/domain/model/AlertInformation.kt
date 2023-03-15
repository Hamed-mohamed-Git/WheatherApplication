package com.example.wheatherapplication.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlertInformation(
    val title:String? = null,
    val senderName:String? = null,
    val event:String? = null,
    val description:String? = null,
): Parcelable
