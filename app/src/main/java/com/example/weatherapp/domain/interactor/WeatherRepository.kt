package com.example.weatherapp.domain.interactor

import androidx.lifecycle.LiveData
import com.example.weatherapp.domain.entities.WeatherResponse

interface WeatherRepository {
    fun getWeather(city: String, units: String): LiveData<WeatherResponse>
}