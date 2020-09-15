package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.WeatherResponse

interface GetWeatherUseCase {
    fun getWeather(): WeatherResponse?
}