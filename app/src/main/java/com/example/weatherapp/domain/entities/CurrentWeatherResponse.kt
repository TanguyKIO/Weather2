package com.example.weatherapp.domain.entities

class CurrentWeatherResponse(
    val weatherModel: WeatherModel?,
    val state: State
)

enum class State {
    LOADING,
    SUCCESS,
    FAILURE,
    NO_DATA
}