package com.example.weatherapp.data.di

import com.example.weatherapp.data.web.WeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
class ApiModule {
    @Provides
    fun bindApiService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }
}