package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.ForecastWeatherAndRecommendationResponse
import com.example.weatherapp.domain.entities.ForecastWeatherResponse
import com.example.weatherapp.domain.entities.WeatherAndRecommendation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetForecastWeatherAndRecommendationImpl @Inject constructor
    (private val weatherRepository: ForecastWeatherRepository,
     private val getRecommendations: GetRecommendations)
    : GetForecastWeatherAndRecommendation {

    override operator fun invoke(): Flow<ForecastWeatherAndRecommendationResponse> {
        return weatherRepository.getForecastWeather(LYON, METRIC)
            .map{
                val weathers = it.weatherModels
                val weathersAndRecommendations = weathers.map {item ->
                    val recommendations = getRecommendations(item)
                    WeatherAndRecommendation(item, recommendations)
                }
                ForecastWeatherAndRecommendationResponse(
                    it.state,
                    weathersAndRecommendations
                )
            }
    }

    private companion object {
        const val LYON = "Lyon"
        const val METRIC = "metric"
    }
}

interface ForecastWeatherRepository {
    fun getForecastWeather(city: String, units: String): Flow<ForecastWeatherResponse>
}