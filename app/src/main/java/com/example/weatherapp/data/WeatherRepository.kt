package com.example.weatherapp.data

import WeatherData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class WeatherRepository() {

    fun getWeather(): LiveData<WeatherResponse> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val data = MutableLiveData<WeatherResponse>()
        val weatherService = retrofit.create(WeatherService::class.java)
        weatherService.getWeather("Lyon", "metric", "31821c1f9bc48389f4dac02cf1829e32")
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    val weatherResponse = WeatherResponse(true, response.body())
                    data.postValue(weatherResponse)
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.e("WeatherRepository", "Problème API call")
                    val weatherResponse = WeatherResponse(false, null)
                }
            })
        return data

    }
}