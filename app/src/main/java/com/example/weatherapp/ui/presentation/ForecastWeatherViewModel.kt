package com.example.weatherapp.ui.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.WeatherAndRecommendation
import com.example.weatherapp.domain.interactor.GetForecastWeatherAndRecommendation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForecastWeatherViewModel @ViewModelInject constructor
    (private val getForecastWeather: GetForecastWeatherAndRecommendation) :
    ViewModel() {

    private val _weathersAndRecommendations = MutableLiveData<List<WeatherAndRecommendation>>()
    val weathersAndRecommendations: LiveData<List<WeatherAndRecommendation>> = _weathersAndRecommendations
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var updateJob: Job? = null

    init {
        update()
    }

    fun update() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            getForecastWeather().collect {
                _weathersAndRecommendations.postValue(it.weatherAndRecommendations)
                _state.postValue(it.state)
            }
        }
    }
}
