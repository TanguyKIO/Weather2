package com.example.weatherapp.ui.presentation


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.Wears
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.interactor.GetCurrentWeatherAndRecommendation
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @ViewModelInject constructor
    (private val getCurrentWeather: GetCurrentWeatherAndRecommendation) :
    ViewModel() {
    private val _weatherModel = MutableLiveData<WeatherModel?>()
    val weatherModel: LiveData<WeatherModel?> = _weatherModel
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    private val _wears = MutableLiveData<List<Wears>>()
    val wears: LiveData<List<Wears>> = _wears

    private var updateJob: Job? = null

    init {
        update()
    }

    fun update() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            getCurrentWeather().collect {
                _weatherModel.postValue(it.weatherAndRecommendation?.weatherModel)
                _wears.postValue(it.weatherAndRecommendation?.wears)
                _state.postValue(it.state)
            }
        }
    }
}
