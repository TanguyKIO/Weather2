package com.example.weatherapp.domain.useCases

import com.example.weatherapp.domain.entities.WeatherResponse

interface GetWeatherUseCase {
    fun getWeather(): WeatherResponse?
}