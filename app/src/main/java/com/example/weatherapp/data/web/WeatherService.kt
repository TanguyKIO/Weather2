package com.example.weatherapp.data.web


import com.example.weatherapp.data.db.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather?")
    fun getWeather(@Query("q") cityName: String, @Query("units")units: String, @Query("appid") key: String): Call<WeatherData>
}