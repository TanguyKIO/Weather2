package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY time DESC LIMIT 1")
    fun getLast(): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weatherEntity: WeatherEntity)
}