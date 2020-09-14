package com.example.weatherapp.domain.useCases

import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.entities.WeatherResponse
import javax.inject.Inject

class GetWeatherUseCaseUseCaseImpl @Inject constructor(private val weatherRepo: WeatherRepository) : GetWeatherUseCase{
    override fun getWeather(): WeatherResponse? {
        return weatherRepo.getWeather()
    }
}