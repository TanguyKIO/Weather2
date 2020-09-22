package com.example.weatherapp.data.di

import com.example.weatherapp.data.repository.CurrentWeatherRepositoryImpl
import com.example.weatherapp.data.repository.ForecastWeatherRepositoryImpl
import com.example.weatherapp.domain.interactor.CurrentWeatherRepository
import com.example.weatherapp.domain.interactor.ForecastWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideCurrentRepo(weatherRepositoryImpl: CurrentWeatherRepositoryImpl): CurrentWeatherRepository

    @Binds
    abstract fun provideForecastRepo(weatherRepositoryImpl: ForecastWeatherRepositoryImpl): ForecastWeatherRepository
}