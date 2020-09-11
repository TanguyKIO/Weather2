package com.example.weatherapp.ui.weather


import android.content.Context
import androidx.lifecycle.*
import androidx.room.Room
import com.example.weatherapp.data.*

private const val TAG = "WeatherViewModel"

class WeatherViewModel(private val context: Context) : ViewModel() {

    private val weatherRepository: WeatherRepository

    private val _weatherModel = MediatorLiveData<WeatherModel>()
    val weatherModel: LiveData<WeatherModel> = _weatherModel


    private var currentSource: LiveData<WeatherModel>? = null

    init {
        val wdb = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, "weather_database"
        ).build()
        val weatherDao = wdb.weatherDao()
        weatherRepository = WeatherRepository(weatherDao)
        update()
    }

    fun update() {
        if (currentSource != null) {
            _weatherModel.removeSource(weatherModel)
        }
        val newSource = weatherRepository.getWeather()
        _weatherModel.addSource(newSource) {
            _weatherModel.postValue(it)
        }
        currentSource = newSource
    }
}