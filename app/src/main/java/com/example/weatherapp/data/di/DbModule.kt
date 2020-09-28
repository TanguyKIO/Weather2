package com.example.weatherapp.data.di

import android.content.Context
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {
    @Singleton
    @Provides
    fun provideDao(@ApplicationContext context: Context): WeatherDao {
        return WeatherDatabase.getInstance(context).weatherDao()
    }
}