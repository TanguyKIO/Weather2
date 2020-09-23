package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.Wears
import com.example.weatherapp.domain.entities.WeatherModel

class GetRecommendationsImpl (private val weatherModel: WeatherModel?):
    GetRecommendations {
    override operator fun invoke(): List<Wears>? {
        return weatherModel?.getRecommendations()
    }
}