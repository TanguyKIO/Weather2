package com.example.weatherapp.data.repository

import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.ForecastWeatherEntity
import com.example.weatherapp.data.web.ForecastWeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.entities.ForecastWeatherResponse
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherType
import com.example.weatherapp.domain.interactor.ForecastWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastWeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : ForecastWeatherRepository {

    override fun getForecastWeather(city: String, units: String): Flow<ForecastWeatherResponse> {
        return flow {
            val cached = weatherDao.getLastForecast(7)
            val reversed = cached.asReversed()
            emit(entityToModel(reversed, State.LOADING, city))
            delay(1000)
            val weatherEntities = remoteToEntity(
                weatherService.getForecastWeather(
                    "45.75",
                    "4.85",
                    units,
                    "current,minutely,hourly",
                    API_KEY
                )
            )
            for (weatherEntity in weatherEntities) {
                weatherDao.saveForecast(weatherEntity)
            }
            val newCache = weatherDao.getLastForecast(weatherEntities.size - 1)
            val data = newCache.asReversed()
            emit(entityToModel(data, State.SUCCESS, city))
        }.catch {
            val cached = weatherDao.getLastForecast(7)
            val reversed = cached.asReversed()
            if (reversed.isEmpty()) {
                emit(entityToModel(reversed, State.NO_DATA, city))
            } else {
                emit(entityToModel(reversed, State.FAILURE, city))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun remoteToEntity(weatherData: ForecastWeatherData): List<ForecastWeatherEntity> {
        val weatherEntities = mutableListOf<ForecastWeatherEntity>()
        for (weather in weatherData.daily) {
            val weatherEntity = ForecastWeatherEntity(
                weather.dt,
                weather.temp.day,
                weather.weather[0].id,
                weather.wind_speed,
                weather.feels_like.day,
                weather.humidity
            )
            weatherEntities.add(weatherEntity)
        }
        return weatherEntities
    }

    private fun entityToModel(
        weatherEntities: List<ForecastWeatherEntity>,
        state: State,
        city: String
    ): ForecastWeatherResponse {
        val weatherModels = mutableListOf<WeatherModel>()
        for (weatherEntity in weatherEntities) {
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

            val millis: Long = weatherEntity.time.toLong() * 1000
            val date = Date(millis)
            val sdf = SimpleDateFormat("EEE d MM", Locale.FRANCE)
            val formattedDate = sdf.format(date)
            val weatherModel = WeatherModel(
                formattedDate,
                city,
                weatherEntity.temp,
                weatherType,
                weatherEntity.windSpeed,
                weatherEntity.feelsLikeTemp,
                weatherEntity.humidity
            )
            weatherModels.add(weatherModel)
        }
        return ForecastWeatherResponse(weatherModels, state)
    }

}