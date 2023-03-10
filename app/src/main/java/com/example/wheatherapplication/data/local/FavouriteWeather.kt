package com.example.wheatherapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(primaryKeys = ["lat", "lng"])
data class FavouriteWeather(
    @ColumnInfo("lat") val lat:String,
    @ColumnInfo("lng") val lng:String,
    @ColumnInfo(name = "weather") val weather: String?,
)

@Entity(primaryKeys = ["lat", "lng"])
data class FavouriteWeatherInformation(
    @ColumnInfo("lat") val lat:String,
    @ColumnInfo("lng") val lng:String,
    @ColumnInfo("workMangerID") val workID:String?,
    @ColumnInfo("alertStart") val alertStart:Long?,
    @ColumnInfo("alertEnd") val alertEnd:Long?
)
