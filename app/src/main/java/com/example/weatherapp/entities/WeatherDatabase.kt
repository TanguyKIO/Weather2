package com.example.weatherapp.entities

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WeatherModel::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}