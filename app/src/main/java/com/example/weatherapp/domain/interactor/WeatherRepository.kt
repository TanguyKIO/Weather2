package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.WeatherResponse

interface WeatherRepository {
    fun getWeather(city: String, units: String): WeatherResponse?
}