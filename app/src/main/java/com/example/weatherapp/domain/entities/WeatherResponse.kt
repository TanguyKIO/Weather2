package com.example.weatherapp.domain.entities

class WeatherResponse(
    val weatherModel: WeatherModel?,
    val state: State
)

enum class State {
    LOADING,
    SUCCESS,
    FAILURE,
    NO_DATA
}