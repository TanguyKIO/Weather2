package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.ForecastWeatherAndRecommendationResponse
import kotlinx.coroutines.flow.Flow

interface GetForecastWeatherAndRecommendation {
    operator fun invoke(): Flow<ForecastWeatherAndRecommendationResponse>
}