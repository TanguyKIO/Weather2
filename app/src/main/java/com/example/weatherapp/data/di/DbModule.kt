package com.example.weatherapp.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDb(app: Application): RoomDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            WeatherDatabase::class.java, "weather_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(weatherDb: WeatherDatabase): WeatherDao {
        return weatherDb.weatherDao()
    }
}