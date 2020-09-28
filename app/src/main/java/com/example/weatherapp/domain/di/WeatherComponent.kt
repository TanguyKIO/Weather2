package com.example.weatherapp.domain.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.data.di.DbModule
import com.example.weatherapp.data.di.NetworkModule
import com.example.weatherapp.data.di.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules =
[AndroidInjectionModule::class, UseCaseModule::class, DbModule::class, NetworkModule::class,
    RepositoryModule::class, FragmentModule::class])
interface WeatherComponent : AndroidInjector<WeatherApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): WeatherComponent
    }

    fun inject(app: Application)
}