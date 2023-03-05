package com.example.wheatherapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavouriteWeather(
    @PrimaryKey val latLngID: String,
    @ColumnInfo(name = "weather") val weather: String?,
)
