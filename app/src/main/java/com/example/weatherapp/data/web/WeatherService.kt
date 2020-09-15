package com.example.weatherapp.data.web


import com.example.weatherapp.data.WEATHER_PATH
import com.example.weatherapp.data.db.WeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// TODO 4.
// create a RemoteWeatherRepresentation
// use it here
interface WeatherService {
    @GET(WEATHER_PATH)
    fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") key: String
    ): Call<WeatherEntity>
}