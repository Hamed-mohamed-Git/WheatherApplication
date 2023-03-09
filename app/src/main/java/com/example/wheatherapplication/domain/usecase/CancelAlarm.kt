package com.example.wheatherapplication.domain.usecase

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class CancelAlarm @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val alarmMangerIntent: Intent
) {

    @SuppressLint("UnspecifiedImmutableFlag")
    operator fun invoke(requestCode:Int){
        alarmManager.cancel(alarmMangerIntent.let {
            PendingIntent.getBroadcast(context, requestCode, it, PendingIntent.FLAG_NO_CREATE)
        })
    }
}