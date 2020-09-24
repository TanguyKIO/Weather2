package com.example.weatherapp.domain.entities

class ForecastWeatherAndRecommendationResponse(
    val state: State,
    val weatherAndRecommendations: List<WeatherAndRecommendation>
)