package com.example.weatherapp.domain.interactor

import com.example.weatherapp.domain.entities.Wears

interface GetRecommendations {
    operator fun invoke(): List<Wears>?
}