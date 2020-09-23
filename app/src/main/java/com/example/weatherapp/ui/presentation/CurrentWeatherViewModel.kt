package com.example.weatherapp.ui.presentation


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.weatherapp.domain.entities.*
import com.example.weatherapp.domain.interactor.GetCurrentWeather
import com.example.weatherapp.domain.interactor.GetRecommendations
import com.example.weatherapp.domain.interactor.GetRecommendationsImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @ViewModelInject constructor(private val getCurrentWeather: GetCurrentWeather) :
    ViewModel() {
    private lateinit var getRecommendations: GetRecommendations
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
                getRecommendations = GetRecommendationsImpl(it.weatherModel)
                _wears.postValue(getRecommendations())
                _weatherModel.postValue(it.weatherModel)
                _state.postValue(it.state)
            }
        }
    }
}