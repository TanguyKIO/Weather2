package com.example.weatherapp.domain.interactor

import androidx.lifecycle.LiveData
import com.example.weatherapp.domain.entities.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface GetWeatherUseCase {
    fun getWeather(): LiveData<WeatherResponse>
}

// TODO 7.
// move to fun getWeather(): Flow<WeatherResponse>