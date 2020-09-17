package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(city: String, units: String): Flow<WeatherResponse>
}