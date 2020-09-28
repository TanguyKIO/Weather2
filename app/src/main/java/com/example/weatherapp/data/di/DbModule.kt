package com.example.weatherapp.data.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    fun provideDao (context: Context): WeatherDao {
        return WeatherDatabase.getInstance(context).weatherDao()
    }
}