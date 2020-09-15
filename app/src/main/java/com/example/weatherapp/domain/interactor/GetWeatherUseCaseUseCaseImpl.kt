package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.WeatherResponse
import javax.inject.Inject

class GetWeatherUseCaseUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    GetWeatherUseCase {

    override fun getWeather(): WeatherResponse? {
        return weatherRepository.getWeather(LYON, METRICS)
    }

    private companion object {
        const val LYON = "lyon"
        const val METRICS = "metrics"
    }
}
