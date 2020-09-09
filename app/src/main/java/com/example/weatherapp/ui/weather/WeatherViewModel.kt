package com.example.weatherapp.ui.weather

import WeatherData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.WeatherRepository
import javax.inject.Inject

private const val TAG = "WeatherViewModel"

class WeatherViewModel @Inject constructor(
) : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private var _weatherData = weatherRepository.getWeather()
    //private val liveDataMerger = MediatorLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> = _weatherData
    //liveDataMerger.addSource(_weatherData)
    /*fun update() {
        _weatherData = weatherRepository.getWeather()

    }*/
}