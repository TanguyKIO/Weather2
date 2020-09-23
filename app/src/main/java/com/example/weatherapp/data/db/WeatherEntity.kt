package com.example.weatherapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentWeatherEntity(
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

@Entity(tableName = "forecast_weather")
data class ForecastWeatherEntity(
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
