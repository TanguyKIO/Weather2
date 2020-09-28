package com.example.weatherapp.data.web


import com.example.weatherapp.CURRENT_WEATHER_PATH
import com.example.weatherapp.FORECAST_WEATHER_PATH
import com.squareup.moshi.Json
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
    @field:Json(name = "coord")
    val coord: Coord,
    @field:Json(name = "weather")
    val weather: List<Weather>,
    @field:Json(name = "base")
    val base: String,
    @field:Json(name = "main")
    val main: Main,
    @field:Json(name = "visibilty")
    val visibility: Int,
    @field:Json(name = "wind")
    val wind: Wind,
    @field:Json(name = "clouds")
    val clouds: Clouds,
    @field:Json(name = "dt")
    val dt: Int,
    @field:Json(name = "sys")
    val sys: Sys,
    @field:Json(name = "timezone")
    val timezone: Int,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "cod")
    val cod: Int
)

data class Main(
    @field:Json(name = "temp")
    val temp: Double,
    @field:Json(name = "feels_like")
    val feelsLike: Double,
    @field:Json(name = "temp_min")
    val tempMin: Double,
    @field:Json(name = "temp_max")
    val tempMax: Double,
    @field:Json(name = "pressure")
    val pressure: Int,
    @field:Json(name = "humidity")
    val humidity: Int
)

data class Clouds(
    @field:Json(name = "all")
    val all: Int
)

data class Coord(
    @field:Json(name = "lon")
    val lon: Double,
    @field:Json(name = "lat")
    val lat: Double
)

data class Weather(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "main")
    val main: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "icon")
    val icon: String
)

data class Sys(
    @field:Json(name = "type")
    val type: Int,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "country")
    val country: String,
    @field:Json(name = "sunrise")
    val sunrise: Int,
    @field:Json(name = "sunset")
    val sunset: Int
)

data class Wind(
    @field:Json(name = "speed")
    val speed: Double,
    @field:Json(name = "deg")
    val deg: Int,
    @field:Json(name = "gust")
    val gust: Double
)

data class ForecastWeatherData(
    @field:Json(name = "lat")
    val lat: Double,
    @field:Json(name = "lon")
    val lon: Double,
    @field:Json(name = "timezone")
    val timezone: String,
    @field:Json(name = "timezone_offset")
    val timezoneOffset: Int,
    @field:Json(name = "daily")
    val daily: List<Daily>
)

data class Daily(
    @field:Json(name = "dt")
    val dt: Int,
    @field:Json(name = "sunrise")
    val sunrise: Int,
    @field:Json(name = "sunset")
    val sunset: Int,
    @field:Json(name = "temp")
    val temp: Temp,
    @field:Json(name = "feels_like")
    val feelsLike: FeelsLike,
    @field:Json(name = "pressure")
    val pressure: Int,
    @field:Json(name = "humidity")
    val humidity: Int,
    @field:Json(name = "dew_point")
    val dewPoint: Double,
    @field:Json(name = "wind_speed")
    val windSpeed: Double,
    @field:Json(name = "wind_deg")
    val windDeg: Int,
    @field:Json(name = "weather")
    val weather: List<Weather>,
    @field:Json(name = "clouds")
    val clouds: Int,
    @field:Json(name = "pop")
    val pop: Double,
    @field:Json(name = "rain")
    val rain: Double,
    @field:Json(name = "uvi")
    val uvi: Double
)

data class Temp(
    @field:Json(name = "day")
    val day: Double,
    @field:Json(name = "min")
    val min: Double,
    @field:Json(name = "max")
    val max: Double,
    @field:Json(name = "night")
    val night: Double,
    @field:Json(name = "eve")
    val eve: Double,
    @field:Json(name = "morn")
    val morn: Double
)

data class FeelsLike(
    @field:Json(name = "day")
    val day: Double,
    @field:Json(name = "night")
    val night: Double,
    @field:Json(name = "eve")
    val eve: Double,
    @field:Json(name = "morn")
    val morn: Double
)

