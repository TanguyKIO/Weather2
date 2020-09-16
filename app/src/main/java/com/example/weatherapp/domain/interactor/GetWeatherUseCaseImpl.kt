package com.example.weatherapp.domain.interactor

import androidx.lifecycle.LiveData
import com.example.weatherapp.domain.entities.WeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    GetWeatherUseCase {

    override fun getWeather(): LiveData<WeatherResponse> {
        return weatherRepository.getWeather(LYON, METRIC)
    }

    private companion object {
        const val LYON = "lyon"
        const val METRIC = "metric"
    }
}
