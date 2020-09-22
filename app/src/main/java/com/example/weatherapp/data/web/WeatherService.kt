package com.example.weatherapp.data.web


import com.example.weatherapp.data.CURRENT_WEATHER_PATH
import com.example.weatherapp.data.FORECAST_WEATHER_PATH
import com.google.gson.annotations.SerializedName

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET(CURRENT_WEATHER_PATH)
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") key: String
    ): CurrentWeatherData

    @GET(FORECAST_WEATHER_PATH)
    suspend fun getForecastWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("exclude") exclusion: String,
        @Query("appid") key: String
    ): ForecastWeatherData
}

data class CurrentWeatherData(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("base")
    val base: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("visibilty")
    val visibility: Int,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("cod")
    val cod: Int
)

data class Main(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("feels_like")
    val feels_like: Double,
    @SerializedName("temp_min")
    val temp_min: Double,
    @SerializedName("temp_max")
    val temp_max: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int
)

data class Clouds(
    @SerializedName("all")
    val all: Int
)

data class Coord(
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("lat")
    val lat: Double
)

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Sys(
    @SerializedName("type")
    val type: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int
)

data class Wind(
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("gust")
    val gust: Double
)

data class ForecastWeatherData(
    @SerializedName("lat")
    val lat : Double,
    @SerializedName("lon")
    val lon : Double,
    @SerializedName("timezone")
    val timezone : String,
    @SerializedName("timezone_offset")
    val timezone_offset : Int,
    @SerializedName("daily")
    val daily : List<Daily>
)

data class Daily (
    @SerializedName("dt")
    val dt : Int,
    @SerializedName("sunrise")
    val sunrise : Int,
    @SerializedName("sunset")
    val sunset : Int,
    @SerializedName("temp")
    val temp : Temp,
    @SerializedName("feels_like")
    val feels_like : FeelsLike,
    @SerializedName("pressure")
    val pressure : Int,
    @SerializedName("humidity")
    val humidity : Int,
    @SerializedName("dew_point")
    val dew_point : Double,
    @SerializedName("wind_speed")
    val wind_speed : Double,
    @SerializedName("wind_deg")
    val wind_deg : Int,
    @SerializedName("weather")
    val weather : List<Weather>,
    @SerializedName("clouds")
    val clouds : Int,
    @SerializedName("pop")
    val pop : Double,
    @SerializedName("rain")
    val rain : Double,
    @SerializedName("uvi")
    val uvi : Double
)

data class Temp (
    @SerializedName("day")
    val day : Double,
    @SerializedName("min")
    val min : Double,
    @SerializedName("max")
    val max : Double,
    @SerializedName("night")
    val night : Double,
    @SerializedName("eve")
    val eve : Double,
    @SerializedName("morn")
    val morn : Double
)

data class FeelsLike (
    @SerializedName("day")
    val day : Double,
    @SerializedName("night")
    val night : Double,
    @SerializedName("eve")
    val eve : Double,
    @SerializedName("morn")
    val morn : Double
)

