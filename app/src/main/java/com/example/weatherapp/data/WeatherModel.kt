package com.example.weatherapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import java.util.*


@Entity(tableName = "weather")
class WeatherModel(
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "weatherId")
    val weather: Int,
    @ColumnInfo(name = "time" +
            "")
    val time: Long,
    var isSuccess: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}