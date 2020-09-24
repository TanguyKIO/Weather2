package com.example.weatherapp.domain.di

import com.example.weatherapp.domain.interactor.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideCurrentWeather(getWeather: GetCurrentWeatherAndRecommendationImpl): GetCurrentWeatherAndRecommendation

    @Binds
    abstract fun provideForecastWeather(getWeather: GetForecastWeatherAndRecommendationImpl): GetForecastWeatherAndRecommendation

    @Binds
    abstract fun provideRecommendations(getRecommendations: GetRecommendationsImpl): GetRecommendations
}