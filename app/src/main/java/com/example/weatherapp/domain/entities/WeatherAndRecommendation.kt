package com.example.weatherapp.domain.entities

data class WeatherAndRecommendation  (
    val weatherModel: WeatherModel,
    val wears: List<Wears>
)