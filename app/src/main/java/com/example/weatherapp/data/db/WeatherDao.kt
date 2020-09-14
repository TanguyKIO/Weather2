package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.domain.entities.WeatherModel


@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE time=(SELECT MAX(time) FROM weather)")
    fun getLast(): WeatherModel

    @Insert
    fun save(weatherModel: WeatherModel)
}