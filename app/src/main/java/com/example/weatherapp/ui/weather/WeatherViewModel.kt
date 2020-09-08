package com.example.weatherapp.ui.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.Weather
import com.example.weatherapp.data.WeatherType

private const val TAG = "WeatherViewModel"

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    fun update(x: Int) {
        val newWeather = Weather(type = WeatherType.CLOUDY, temperature = x)
        _weather.postValue(newWeather)
        Log.e(TAG, "weather set: $newWeather")
    }
}