package com.example.weatherapp.data.di;


import com.example.weatherapp.data.BASE_URL
import com.example.weatherapp.data.web.WeatherService
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

}