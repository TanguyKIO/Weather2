package com.example.weatherapp.domain

import com.example.weatherapp.domain.entities.WeatherResponse

interface RemoteRepository {
    fun getWeather(): WeatherResponse?
}