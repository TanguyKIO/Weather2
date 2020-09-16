package com.example.weatherapp.data.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.R
import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherEntity
import com.example.weatherapp.data.web.Weather
import com.example.weatherapp.data.web.WeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.interactor.WeatherRepository
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherResponse
import com.example.weatherapp.domain.entities.WeatherType
import kotlinx.android.synthetic.main.weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors.newCachedThreadPool
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getWeather(city: String, units: String): LiveData<WeatherResponse> {
        val executor: Executor = newCachedThreadPool()

        val data = MutableLiveData<WeatherResponse>()

        weatherService.getWeather(city, units, API_KEY)
            .enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    executor.execute {
                        val remote = response.body()
                        val weatherEntity = remote?.let { remoteToEntity(it) }
                        if (weatherEntity != null) {
                            weatherDao.save(weatherEntity)
                            data.postValue(entityToModel(weatherEntity, true))
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    executor.execute {
                        Log.e("WeatherRepository", "Problème API call")
                        val weatherEntity = weatherDao.getLast()
                        data.postValue(entityToModel(weatherEntity, false))
                    }
                }
            })

        return data

    }

    fun remoteToEntity(weatherData: WeatherData): WeatherEntity {
        return WeatherEntity(
            weatherData.dt,
            weatherData.main.temp,
            weatherData.weather[0].id,
            weatherData.wind.speed,
            weatherData.main.feels_like,
            weatherData.main.humidity
        )
    }

    fun entityToModel(weatherEntity: WeatherEntity, isSucces: Boolean): WeatherResponse {
        val weatherType: WeatherType = when (weatherEntity.weather) {
            in 200..232 -> WeatherType.THUNDERSTORM
            in 300..321 -> WeatherType.DRIZZLE
            in 500..531 -> WeatherType.RAIN
            in 600..622 -> WeatherType.SNOW
            in 701..781 -> WeatherType.FOG
            800 -> WeatherType.CLEAR
            in 801..804 -> WeatherType.CLOUDS
            else -> WeatherType.UNKNOWN
        }
        return WeatherResponse(
            isSucces,
            WeatherModel(
                weatherEntity.time,
                weatherEntity.temp,
                weatherType,
                weatherEntity.windSpeed,
                weatherEntity.feelsLikeTemp,
                weatherEntity.humidity
            )
        )
    }
}