package com.example.weatherapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    //Comprendre comment add le context et comment provide l'api
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