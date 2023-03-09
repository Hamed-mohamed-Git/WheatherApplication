package com.example.wheatherapplication.domain.usecase

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.domain.model.AlertInformation
import javax.inject.Inject

class SetAlarm @Inject constructor(
    private val context: Context,
    private val alarmManager: AlarmManager,
    private val alarmManagerIntent: Intent
) {

    operator fun invoke(time: Long, alertInformation: AlertInformation, requestCode:Int) =
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            alarmManagerIntent
                .let { intent ->
                    intent.putExtra(
                        Constants.BROADCAST_RECEIVER_INTENT_KEY,
                        Bundle().apply { putParcelable(Constants.BROADCAST_RECEIVER_INTENT_KEY, alertInformation) })
                    PendingIntent.getBroadcast(context, requestCode, intent, 0)
                }
        )


}