package com.example.weatherapp.data.repository


import android.util.Log
import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherEntity
import com.example.weatherapp.data.web.WeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.interactor.WeatherRepository
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherResponse
import com.example.weatherapp.domain.entities.WeatherType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : WeatherRepository {

    override fun getWeather(city: String, units: String): Flow<WeatherResponse> {
        return flow {
            val weatherEntity = remoteToEntity(weatherService.getWeather(city, units, API_KEY))
            weatherDao.save(weatherEntity)
            emit(entityToModel(weatherEntity,true))
        }.catch{
            emit(entityToModel(weatherDao.getLast(),false))
        }.flowOn(Dispatchers.IO)
    }

    private fun remoteToEntity(weatherData: WeatherData): WeatherEntity {
        return WeatherEntity(
            weatherData.dt,
            weatherData.main.temp,
            weatherData.weather[0].id,
            weatherData.wind.speed,
            weatherData.main.feels_like,
            weatherData.main.humidity
        )
    }

    private fun entityToModel(weatherEntity: WeatherEntity, isSucces: Boolean): WeatherResponse {
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