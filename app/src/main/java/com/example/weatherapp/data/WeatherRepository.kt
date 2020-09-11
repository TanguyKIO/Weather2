package com.example.weatherapp.data


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newCachedThreadPool


class WeatherRepository(private val weatherDao: WeatherDao) {

    fun getWeather(): LiveData<WeatherModel> {

        val executor: Executor = newCachedThreadPool()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val data = MutableLiveData<WeatherModel>()
        Log.e("WeatherRepository", "RE")
        val weatherService = retrofit.create(WeatherService::class.java)
        weatherService.getWeather("Lyon", "metric", "31821c1f9bc48389f4dac02cf1829e32")
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    executor.execute {
                        val weatherResponse = WeatherResponse(true, response.body())
                        val weatherModel = weatherResponse.weatherData?.let { remoteToModel(it, 1) }
                        data.postValue(weatherModel)
                        if (weatherModel != null) {
                            weatherDao.save(weatherModel)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    executor.execute {
                        Log.e("WeatherRepository", "Problème API call")
                        val weatherModel = weatherDao.getLast()
                        if (weatherModel != null) {
                            Log.d("WeatherRepository", "YOUPI")
                            weatherModel.isSuccess = 0
                        }
                        data.postValue(weatherModel)
                    }
                }
            })

        return data

    }

    fun remoteToModel(weatherData: WeatherData, code: Int): WeatherModel {
        val temp = weatherData.main.temp
        val weather = weatherData.weather[0].id
        return WeatherModel(temp, weather, System.currentTimeMillis(), code)
    }
}