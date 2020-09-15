package com.example.weatherapp.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

// TODO 1.
// this should not have any reference to room, this should NOT be the database entity
// this is pure kotlin
// keep only the representation that make sense for us
//  - time => Date
//  - weather type (Thunderstorm/Drizzle/Rain/Snow/Atmosphere*(5?)/Clear/Clouds) => enum (to be created)
//  - temperature => Double
//  - feels like temperature (bonus) => Double
//  - wind speed (bonus) => Double (or other number format)
//  - humidity (bonus) => Int (in [0; 100])
@Entity(tableName = "weather")
class WeatherModel(
    @ColumnInfo(name = "temp")
    val temp: Double,
    @ColumnInfo(name = "weatherId")
    val weather: Int,
    @ColumnInfo(name = "time")
    val time: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}