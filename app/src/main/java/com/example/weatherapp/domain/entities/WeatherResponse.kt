package com.example.weatherapp.domain.entities

class WeatherResponse(
    var isSuccess: Boolean,
    val weatherModel: WeatherModel?
)