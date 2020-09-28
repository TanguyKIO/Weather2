package com.example.weatherapp.domain.di

import com.example.weatherapp.ui.presentation.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeFragment(): WeatherFragment
}