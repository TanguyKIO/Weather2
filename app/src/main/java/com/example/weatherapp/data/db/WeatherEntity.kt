package com.example.weatherapp.data.db

import com.example.weatherapp.domain.entities.Coord
import com.example.weatherapp.domain.entities.Main
import com.example.weatherapp.domain.entities.Weather

// TODO 2 => this should be the room entity
data class WeatherEntity(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val dt: Int,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)