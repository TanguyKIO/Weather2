package com.example.weatherapp.ui.weather

import WeatherData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherResponse
import javax.inject.Inject

private const val TAG = "WeatherViewModel"

class WeatherViewModel() : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val _weatherData = MediatorLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData

    private var currentSource: LiveData<WeatherResponse>? = null

    init {
        update()
    }

    fun update() {
        if (currentSource != null) {
            _weatherData.removeSource(weatherData)
        }
        val newSource = weatherRepository.getWeather()
        _weatherData.addSource(newSource) {
            _weatherData.postValue(it.weatherData)
        }
        currentSource = newSource
    }
}