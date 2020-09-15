package com.example.weatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.domain.entities.WeatherModel


// TODO 3.
// use the newly created entity for the database
@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY time DESC LIMIT 1")
    fun getLast(): WeatherModel

    @Insert
    fun save(weatherModel: WeatherModel)
}