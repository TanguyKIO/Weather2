package com.example.weatherapp.ui.presentation


import androidx.lifecycle.*
import com.example.weatherapp.domain.entities.*
import com.example.weatherapp.domain.interactor.GetWeatherUseCase
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {

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
        var newSource = MutableLiveData<WeatherResponse>()
        newSource.value = getWeatherUseCase.getWeather()
        _weatherModel.addSource(newSource) {
            _weatherModel.postValue(it)
        }
        currentSource = newSource
    }
}