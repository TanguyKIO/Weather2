package com.example.weatherapp.data.di

import com.example.weatherapp.data.repository.WeatherRepository
import com.example.weatherapp.domain.RemoteRepository
import dagger.Binds

abstract class RepositoryModule {
    @Binds
    abstract fun provideRepo(weatherRepository: WeatherRepository): RemoteRepository
}