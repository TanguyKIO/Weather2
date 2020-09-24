package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.Wears
import com.example.weatherapp.domain.entities.WeatherModel

interface GetRecommendations {
    operator fun invoke(weather: WeatherModel): List<Wears>
}