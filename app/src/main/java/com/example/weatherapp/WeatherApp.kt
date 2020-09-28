package com.example.weatherapp

import com.example.weatherapp.domain.di.DaggerWeatherComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class WeatherApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerWeatherComponent
            .factory()
            .create(applicationContext)
}