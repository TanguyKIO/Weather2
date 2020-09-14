package com.example.weatherapp.domain.di

import com.example.weatherapp.domain.useCases.GetWeatherUseCase
import com.example.weatherapp.domain.useCases.GetWeatherUseCaseUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class UseCaseModule {
    @Binds
    abstract fun provideWeather(getWeather: GetWeatherUseCaseUseCaseImpl): GetWeatherUseCase
}