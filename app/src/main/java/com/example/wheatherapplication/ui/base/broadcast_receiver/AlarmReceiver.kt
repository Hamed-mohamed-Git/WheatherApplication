package com.example.wheatherapplication.ui.base.broadcast_receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.wheatherapplication.R
import com.example.wheatherapplication.constants.Constants
import com.example.wheatherapplication.domain.model.AlertInformation


class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("UseCompatLoadingForDrawables", "ObsoleteSdkInt")
    override fun onReceive(context: Context?, intent: Intent?) {
        (intent?.getBundleExtra(Constants.BROADCAST_RECEIVER_INTENT_KEY)
            ?.getParcelable<AlertInformation>(Constants.BROADCAST_RECEIVER_INTENT_KEY) as AlertInformation).let {
            with((context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)) {
                val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel(
                        Constants.BROADCAST_RECEIVER_CHANNEL_ID,
                        "Emergency Alert",
                        NotificationManager.IMPORTANCE_HIGH
                    ).let { notificationChannel ->
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.GREEN
                        notificationChannel.enableVibration(false)
                        createNotificationChannel(notificationChannel)
                    }
                    NotificationCompat.Builder(context, Constants.BROADCAST_RECEIVER_CHANNEL_ID)
                } else {
                    NotificationCompat.Builder(context)
                }
                builder.apply {
                    setContentTitle("Emergency Alert")
                    setContentText(it.senderName)
                    setLargeIcon(context.getDrawable(R.drawable.alert)?.toBitmap())
                    setSmallIcon(R.drawable.ic_launcher_foreground)
                    setStyle(NotificationCompat.BigTextStyle().bigText(it.description))
                }
                notify(1234, builder.build())
            }
        }
    }
}
