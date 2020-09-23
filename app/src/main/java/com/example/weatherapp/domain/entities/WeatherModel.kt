package com.example.weatherapp.domain.entities


class WeatherModel(
    val time: String,
    val city: String,
    val temp: Double,
    val weather: WeatherType,
    val windSpeed: Double,
    val feelsLikeTemp: Double,
    val humidity: Int? //1..100
) {
    fun getRecommendations(): List<Wears>{
        val recommendation = mutableListOf<Wears>()
        if (this != null) {
            if (this.weather == WeatherType.CLEAR) {
                recommendation.add(Wears.SUNGLASSES)
            }
            if (this.temp < 5) {
                recommendation.add(Wears.WINTER_JACKET)
            }
            if (this.temp > 20) {
                recommendation.add(Wears.SWEATER)
            }
            if (this.windSpeed > 20 && this.temp < 25 && this.temp >= 5) {
                recommendation.add(Wears.WINDBREAKER)
            }
        }
        return recommendation
    }
}

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