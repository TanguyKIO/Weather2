package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.ForecastWeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetForecastWeather {
    operator fun invoke(): Flow<ForecastWeatherResponse>
}