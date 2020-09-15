package com.example.weatherapp.data.repository


import android.util.Log
import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherEntity
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.interactor.WeatherRepository
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newCachedThreadPool
import javax.inject.Inject
import javax.inject.Singleton

// TODO 5.
// use all the representation
// the remote one for the api call
// the entity for the dao/database related call
// the domain one for the return type
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getWeather(city: String, units: String): WeatherResponse? {
        val executor: Executor = newCachedThreadPool()

        var data: WeatherResponse? = null

        weatherService.getWeather(city, units, API_KEY)
            .enqueue(object : Callback<WeatherEntity> {
                override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
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

                override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
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

    fun remoteToModel(weatherEntity: WeatherEntity): WeatherModel {
        val temp = weatherEntity.main.temp
        val weather = weatherEntity.weather[0].id
        return WeatherModel(
            temp,
            weather,
            System.currentTimeMillis()
        )
    }
}