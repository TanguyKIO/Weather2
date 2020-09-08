package com.example.weatherapp.data

data class Weather(
    val type: WeatherType,
    val temperature: Int
)

enum class WeatherType {
    SUN,
    CLOUDY
}