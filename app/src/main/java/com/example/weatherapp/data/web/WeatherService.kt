package com.example.weatherapp.data.web


import com.example.weatherapp.data.WEATHER_PATH
import com.example.weatherapp.data.db.WeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(WEATHER_PATH)
    fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") key: String
    ): Call<WeatherData>
}

data class WeatherData(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)
