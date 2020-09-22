package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetCurrentWeather {
    operator fun invoke(): Flow<CurrentWeatherResponse>
}