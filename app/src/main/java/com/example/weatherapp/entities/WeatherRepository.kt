package com.example.weatherapp.entities


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newCachedThreadPool


class WeatherRepository(private val weatherDao: WeatherDao) {

    fun getWeather(): LiveData<WeatherResponse> {

        val executor: Executor = newCachedThreadPool()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val data = MutableLiveData<WeatherResponse>()
        val weatherService = retrofit.create(WeatherService::class.java)
        weatherService.getWeather("Lyon", "metric", "31821c1f9bc48389f4dac02cf1829e32")
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    executor.execute {
                        val remote = response.body()
                        val weatherModel = remote?.let { remoteToModel(it) }
                        val result =
                            WeatherResponse(
                                true,
                                weatherModel
                            )
                        data.postValue(result)
                        if (weatherModel != null) {
                            weatherDao.save(weatherModel)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    executor.execute {
                        Log.e("WeatherRepository", "Problème API call")
                        val weatherModel = weatherDao.getLast()
                        val result =
                            WeatherResponse(
                                false,
                                weatherModel
                            )
                        data.postValue(result)
                    }
                }
            })

        return data

    }

    fun remoteToModel(weatherData: WeatherData): WeatherModel {
        val temp = weatherData.main.temp
        val weather = weatherData.weather[0].id
        return WeatherModel(
            temp,
            weather,
            System.currentTimeMillis()
        )
    }
}