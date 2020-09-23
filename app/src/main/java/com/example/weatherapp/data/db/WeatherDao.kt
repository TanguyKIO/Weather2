package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {
    @Query("SELECT * FROM current_weather ORDER BY time DESC LIMIT 1")
    fun getLastCurrent(): CurrentWeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrent(weatherEntity: CurrentWeatherEntity)

    @Query("SELECT * FROM forecast_weather ORDER BY time DESC LIMIT :limit")
    fun getLastForecast(limit: Int): List<ForecastWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveForecast(weatherEntity: ForecastWeatherEntity)
}