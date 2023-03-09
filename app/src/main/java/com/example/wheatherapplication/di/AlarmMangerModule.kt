package com.example.wheatherapplication.di

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.wheatherapplication.ui.base.broadcast_receiver.AlarmReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AlarmMangerModule {


    @Provides
    fun provideAlarmManger(context: Context): AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    @Provides
    fun provideAlarmMangerIntent(context: Context): Intent =
        Intent(context, AlarmReceiver::class.java)
}