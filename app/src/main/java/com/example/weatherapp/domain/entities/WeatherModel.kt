package com.example.weatherapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo


@Entity(tableName = "weather")
class WeatherModel(
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "weatherId")
    val weather: Int,
    @ColumnInfo(name = "time")
    val time: Long
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}