package com.example.weatherapp.data.repository


import android.util.Log
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.RemoteRepository
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newCachedThreadPool
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
): RemoteRepository {

    override fun getWeather(): WeatherResponse? {
        val executor: Executor = newCachedThreadPool()

        var data: WeatherResponse? = null

        weatherService.getWeather("Lyon", "metric", "31821c1f9bc48389f4dac02cf1829e32")
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    executor.execute {
                        val remote = response.body()
                        val weatherModel = remote?.let { remoteToModel(it) }
                        data =
                            WeatherResponse(
                                true,
                                weatherModel
                            )
                        if (weatherModel != null) {
                            weatherDao.save(weatherModel)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    executor.execute {
                        Log.e("WeatherRepository", "Problème API call")
                        val weatherModel = weatherDao.getLast()
                        data =
                            WeatherResponse(
                                false,
                                weatherModel
                            )
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