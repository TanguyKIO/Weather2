package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentWeatherAndRecommendationImpl @Inject constructor(
    private val weatherRepository: CurrentWeatherRepository,
    private val getRecommendations: GetRecommendations
) :
    GetCurrentWeatherAndRecommendation {

    override operator fun invoke(): Flow<CurrentWeatherAndRecommendationResponse> {
        return weatherRepository
            .getCurrentWeather(LYON, METRIC)
            .map {
                CurrentWeatherAndRecommendationResponse(
                    it.state,
                    it.weatherModel?.let { it1 ->
                        WeatherAndRecommendation(
                            it1,
                            getRecommendations(it.weatherModel)
                        )
                    }
                )
            }
    }

    private companion object {
        const val LYON = "Lyon"
        const val METRIC = "metric"
    }
}

interface CurrentWeatherRepository {
    fun getCurrentWeather(city: String, units: String): Flow<CurrentWeatherResponse>
}
