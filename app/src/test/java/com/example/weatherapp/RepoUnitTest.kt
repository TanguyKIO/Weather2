package com.example.weatherapp

import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.data.web.WeatherService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RepoUnitTest {
    @Test
    fun whenNoWifi(){
        val weatherService = Mockito.mock(WeatherService::class.java)
        val weatherDao = Mockito.mock(WeatherDao::class.java)
        val weatherRepo = WeatherRepositoryImpl(weatherService, weatherDao)
        Mockito.`when`(weatherService.getWeather("Lyon","Metric", API_KEY)).thenThrow(
            HttpException(Response.error(1, "ah"
                .toResponseBody("oh".toMediaTypeOrNull())))
        )

        weatherRepo.getWeather("London","metric")
        verify(weatherDao).getLast()
    }

}