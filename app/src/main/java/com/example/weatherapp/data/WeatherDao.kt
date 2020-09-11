package com.example.weatherapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather WHERE time=(SELECT MAX(time) FROM weather)")
    fun getLast(): WeatherModel

    @Insert
    fun save(weatherModel: WeatherModel)

    @Delete
    fun deleteWeatherModel(weatherModel: WeatherModel)

}