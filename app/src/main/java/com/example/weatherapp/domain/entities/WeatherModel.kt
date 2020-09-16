package com.example.weatherapp.domain.entities


class WeatherModel(
    val time: Int,
    val temp: Double,
    val weather: WeatherType,
    val windSpeed: Double,
    val feelsLikeTemp: Double,
    val humidity: Int //1..100
)

enum class WeatherType {
    THUNDERSTORM,
    DRIZZLE,
    RAIN,
    SNOW,
    CLEAR,
    CLOUDS,
    FOG,
    UNKNOWN
}