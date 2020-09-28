package com.example.weatherapp.domain.di

import com.example.weatherapp.domain.interactor.GetCurrentWeatherAndRecommendation
import com.example.weatherapp.domain.interactor.GetCurrentWeatherAndRecommendationImpl
import com.example.weatherapp.domain.interactor.GetForecastWeatherAndRecommendation
import com.example.weatherapp.domain.interactor.GetForecastWeatherAndRecommendationImpl
import com.example.weatherapp.domain.interactor.GetRecommendations
import com.example.weatherapp.domain.interactor.GetRecommendationsImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun provideCurrentWeather(getWeather: GetCurrentWeatherAndRecommendationImpl)
            : GetCurrentWeatherAndRecommendation

    @Binds
    abstract fun provideForecastWeather(getWeather: GetForecastWeatherAndRecommendationImpl)
            : GetForecastWeatherAndRecommendation

    @Binds
    abstract fun provideRecommendations(getRecommendations: GetRecommendationsImpl)
            : GetRecommendations
}