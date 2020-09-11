package com.example.weatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

class WeatherTimeStampDao {
    @Dao
    abstract class TimestampDataDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        abstract fun insert(weatherModel: WeatherModel)

        fun insertWithTimestamp(weatherModel: WeatherModel) {
            insert(weatherModel.apply{
                //createdAt = System.currentTimeMillis()
            })
        }
    }
}