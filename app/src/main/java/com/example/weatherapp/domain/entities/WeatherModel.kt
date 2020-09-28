package com.example.weatherapp.domain.entities


data class WeatherModel(
    val time: String,
    val city: String,
    val temp: Double,
    val weather: WeatherType,
    val windSpeed: Double,
    val feelsLikeTemp: Double,
    val humidity: Int? //1..100
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

enum class Wears {
    SUNGLASSES,
    SWEATER,
    WINDBREAKER,
    WINTER_JACKET
}