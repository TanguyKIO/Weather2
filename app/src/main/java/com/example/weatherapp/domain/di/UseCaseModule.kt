package com.example.weatherapp.domain.di

import com.example.weatherapp.domain.interactor.GetCurrentWeather
import com.example.weatherapp.domain.interactor.GetCurrentWeatherImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideWeather(getWeather: GetCurrentWeatherImpl): GetCurrentWeather
}