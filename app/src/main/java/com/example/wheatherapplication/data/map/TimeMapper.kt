package com.example.wheatherapplication.data.map

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wheatherapplication.constants.Constants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object TimeMapper {

     fun convertTimeStamp(timeStamp:Long) : Date  =
        Date(timeStamp * 1000)

    @SuppressLint("SimpleDateFormat")
    fun convertTimeStampToHour(timeStamp:Long?) : String =
        SimpleDateFormat(Constants.DATE_HOUR_PATTERN, Locale.getDefault()).format(convertTimeStamp(timeStamp ?: 0L))

    @SuppressLint("SimpleDateFormat")
    fun convertTimeStampToDate(timeStamp:Long?) : String =
        SimpleDateFormat(Constants.DATE_DAY_PATTERN,Locale.getDefault()).format(convertTimeStamp(timeStamp ?: 0L))

    @SuppressLint("SimpleDateFormat")
    fun convertTimeStampToFullDate(timeStamp:Long?) : String =
        SimpleDateFormat(Constants.FULL_DATE_PATTERN,Locale.getDefault()).format(convertTimeStamp(timeStamp ?: 0L))


    @RequiresApi(Build.VERSION_CODES.O)
     fun convertTimeStampToLocalDate(timeStamp:Long?): LocalDate =
        LocalDate.parse(convertTimeStampToFullDate(timeStamp) , DateTimeFormatter.ofPattern(Constants.FULL_DATE_PATTERN))


}