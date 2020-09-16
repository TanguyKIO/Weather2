package com.example.weatherapp.domain.di

import com.example.weatherapp.domain.interactor.GetWeatherUseCase
import com.example.weatherapp.domain.interactor.GetWeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideWeather(getWeather: GetWeatherUseCaseImpl): GetWeatherUseCase
}