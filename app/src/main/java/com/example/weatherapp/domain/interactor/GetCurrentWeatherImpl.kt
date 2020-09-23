package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.CurrentWeatherResponse
import com.example.weatherapp.domain.entities.Wears
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentWeatherImpl @Inject constructor(private val weatherRepository: CurrentWeatherRepository) :
    GetCurrentWeather {

    override operator fun invoke(): Flow<CurrentWeatherResponse> {
        return weatherRepository.getCurrentWeather(LYON, METRIC)
    }

    private companion object {
        const val LYON = "Lyon"
        const val METRIC = "metric"
    }
}

interface CurrentWeatherRepository {
    fun getCurrentWeather(city: String, units: String): Flow<CurrentWeatherResponse>

    fun getRecommendations() : List<Wears>
}
