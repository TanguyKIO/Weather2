package com.example.weatherapp.domain.interactor

import com.example.weatherapp.MAX_TEMP
import com.example.weatherapp.MAX_TEMP_WIND
import com.example.weatherapp.MIN_TEMP
import com.example.weatherapp.MIN_WIND
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
        if (weather.temp < MAX_TEMP) {
            recommendation.add(Wears.WINTER_JACKET)
        }
    if (weather.temp > MIN_TEMP) {
            recommendation.add(Wears.SWEATER)
        }
        if (weather.windSpeed > MIN_WIND && weather.temp < MAX_TEMP_WIND && weather.temp >= MAX_TEMP) {
            recommendation.add(Wears.WINDBREAKER)
        }
        return recommendation
    }

}