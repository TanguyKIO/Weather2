package com.example.weatherapp.ui.presentation


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weatherapp.domain.entities.*
import com.example.weatherapp.domain.interactor.GetWeatherUseCase

class WeatherViewModel @ViewModelInject constructor(private val getWeatherUseCase: GetWeatherUseCase) :
    ViewModel() {

    private val _weatherModel = MediatorLiveData<WeatherResponse>()
    val weatherModel: LiveData<WeatherResponse> = _weatherModel

    private var currentSource: LiveData<WeatherResponse>? = null

    init {
        update()
    }

    fun update() {
        if (currentSource != null) {
            _weatherModel.removeSource(weatherModel)
        }
        val newSource = getWeatherUseCase.getWeather()
        _weatherModel.addSource(newSource) {
            _weatherModel.postValue(it)
        }
        currentSource = newSource
    }
}