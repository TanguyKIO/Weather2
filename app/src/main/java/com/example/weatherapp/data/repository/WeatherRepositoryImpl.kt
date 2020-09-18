package com.example.weatherapp.data.repository


import android.util.Log
import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.WeatherEntity
import com.example.weatherapp.data.web.WeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherResponse
import com.example.weatherapp.domain.entities.WeatherType
import com.example.weatherapp.domain.interactor.CurrentWeatherRepository
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
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : CurrentWeatherRepository {

    override fun getCurrentWeather(city: String, units: String): Flow<WeatherResponse> {
        return flow {
            val cached = weatherDao.getLast()
            emit(entityToModel(cached, State.LOADING))
            delay(3_000)
            val weatherEntity = remoteToEntity(weatherService.getWeather(city, units, API_KEY))
            weatherDao.save(weatherEntity)
            emit(entityToModel(weatherEntity, State.SUCCESS))
        }.catch {
            val cached = weatherDao.getLast()
            if (cached == null) {
                emit(entityToModel(cached, State.NO_DATA))
            } else {
                emit(entityToModel(cached, State.FAILURE))
            }
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

    private fun entityToModel(weatherEntity: WeatherEntity?, state: State): WeatherResponse {
        if (weatherEntity == null) {
            return WeatherResponse(null, state)
        } else {
            val weatherType: WeatherType = when (weatherEntity?.weather) {
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
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.FRANCE)
            val formattedDate = sdf.format(date)
            Log.e("WeatherRepositoryImpl","${millis}, ${weatherEntity.time}" + formattedDate)
            return WeatherResponse(
                WeatherModel(
                    formattedDate,
                    weatherEntity.temp,
                    weatherType,
                    weatherEntity.windSpeed,
                    weatherEntity.feelsLikeTemp,
                    weatherEntity.humidity
                ), state
            )
        }
    }
}