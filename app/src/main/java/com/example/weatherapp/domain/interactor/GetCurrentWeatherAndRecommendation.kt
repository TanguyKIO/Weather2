package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.CurrentWeatherAndRecommendationResponse
import com.example.weatherapp.domain.entities.CurrentWeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetCurrentWeatherAndRecommendation {
    operator fun invoke(): Flow<CurrentWeatherAndRecommendationResponse>
}