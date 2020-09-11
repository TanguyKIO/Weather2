package com.example.weatherapp.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class WeatherDataConverter {
    companion object {

        @JvmStatic
        @TypeConverter
        fun fromWeatherData(weatherData: WeatherData) = Gson().toJson(weatherData)

        @JvmStatic
        @TypeConverter
        fun toHighlightingColor(s: String): WeatherData =
            Gson().fromJson(s, WeatherData::class.java)

    }
}