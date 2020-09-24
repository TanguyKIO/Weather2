package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.ForecastWeatherAndRecommendationResponse
import com.example.weatherapp.domain.entities.ForecastWeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetForecastWeatherAndRecommendation {
    operator fun invoke(): Flow<ForecastWeatherAndRecommendationResponse>
}