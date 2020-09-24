package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.Wears
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherType
import javax.inject.Inject

class GetRecommendationsImpl @Inject constructor() : GetRecommendations {
    override operator fun invoke(weather: WeatherModel): List<Wears> {
        val recommendation = mutableListOf<Wears>()
        if (weather.weather == WeatherType.CLEAR) {
            recommendation.add(Wears.SUNGLASSES)
        }
        if (weather.temp < 5) {
            recommendation.add(Wears.WINTER_JACKET)
        }
        if (weather.temp > 20) {
            recommendation.add(Wears.SWEATER)
        }
        if (weather.windSpeed > 20 && weather.temp < 25 && weather.temp >= 5) {
            recommendation.add(Wears.WINDBREAKER)
        }
        return recommendation
    }

}