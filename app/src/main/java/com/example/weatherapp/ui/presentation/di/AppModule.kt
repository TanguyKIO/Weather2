package com.example.weatherapp.ui.presentation.di

import android.app.Application
import android.content.Context
import dagger.Binds

abstract class AppModule {
    @Binds
    abstract fun providesContext(app: Application): Context
}