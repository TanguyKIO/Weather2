package com.example.weatherapp.ui.presentation


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weatherapp.domain.entities.*
import com.example.weatherapp.domain.interactor.GetCurrentWeather
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @ViewModelInject constructor(private val getWeatherUseCase: GetCurrentWeather) :
    ViewModel() {

    private val _weatherModel = MutableLiveData<WeatherModel?>()
    val weatherModel: LiveData<WeatherModel?> = _weatherModel
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var updateJob: Job? = null

    init {
        update()
    }

    fun update() {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            getWeatherUseCase().collect {
                _weatherModel.postValue(it.weatherModel)
                _state.postValue(it.state)
            }
        }
    }
}