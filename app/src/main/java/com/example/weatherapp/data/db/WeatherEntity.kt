package com.example.weatherapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "time")
    val time: Int,
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "weatherId")
    val weather: Int,
    @ColumnInfo(name = "wind-speed")
    val windSpeed: Double,
    @ColumnInfo(name = "feels-like")
    val feelsLikeTemp: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Int
)
