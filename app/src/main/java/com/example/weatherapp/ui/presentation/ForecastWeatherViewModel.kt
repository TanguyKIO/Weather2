package com.example.weatherapp.ui.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.interactor.GetForecastWeather
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ForecastWeatherViewModel @ViewModelInject constructor(private val getForecastWeather: GetForecastWeather) :
    ViewModel() {

    private val _weatherModels = MutableLiveData<List<WeatherModel>?>()
    val weatherModels: LiveData<List<WeatherModel>?> = _weatherModels
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
                _weatherModels.postValue(it.weatherModels)
                _state.postValue(it.state)
            }
        }
    }
}