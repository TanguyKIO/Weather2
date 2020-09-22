package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.ForecastWeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastWeatherImpl @Inject constructor(private val weatherRepository: ForecastWeatherRepository) :
    GetForecastWeather {

    override operator fun invoke(): Flow<ForecastWeatherResponse> {
        return weatherRepository.getForecastWeather(LYON, METRIC)
    }

    private companion object {
        const val LYON = "Lyon"
        const val METRIC = "metric"
    }
}

interface ForecastWeatherRepository {
    fun getForecastWeather(city: String, units: String): Flow<ForecastWeatherResponse>
}