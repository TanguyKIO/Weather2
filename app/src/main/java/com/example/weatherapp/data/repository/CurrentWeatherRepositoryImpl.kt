package com.example.weatherapp.data.repository


import com.example.weatherapp.CLOUDS_MAX
import com.example.weatherapp.CLOUDS_MIN
import com.example.weatherapp.SUN
import com.example.weatherapp.FOG_MAX
import com.example.weatherapp.FOG_MIN
import com.example.weatherapp.SNOW_MAX
import com.example.weatherapp.SNOW_MIN
import com.example.weatherapp.RAIN_MAX
import com.example.weatherapp.RAIN_MIN
import com.example.weatherapp.DRIZZLE_MAX
import com.example.weatherapp.DRIZZLE_MIN
import com.example.weatherapp.THUNDER_MAX
import com.example.weatherapp.THUNDER_MIN
import com.example.weatherapp.API_KEY
import com.example.weatherapp.K
import com.example.weatherapp.data.db.CurrentWeatherEntity
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.web.CurrentWeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.entities.CurrentWeatherResponse
import com.example.weatherapp.domain.entities.State
import com.example.weatherapp.domain.entities.WeatherModel
import com.example.weatherapp.domain.entities.WeatherType
import com.example.weatherapp.domain.interactor.CurrentWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : CurrentWeatherRepository {

    override fun getCurrentWeather(city: String, units: String): Flow<CurrentWeatherResponse> {
        return flow {
            val cached = weatherDao.getLastCurrent()
            emit(entityToModel(cached, State.LOADING, city))
            val weatherEntity =
                remoteToEntity(weatherService.getCurrentWeather(city, units,
                    API_KEY
                ))
            weatherDao.saveCurrent(weatherEntity)
            emit(entityToModel(weatherEntity, State.SUCCESS, city))
        }.catch {
            val cached = weatherDao.getLastCurrent()
            if (cached == null) {
                emit(entityToModel(cached, State.NO_DATA, city))
            } else {
                emit(entityToModel(cached, State.FAILURE, city))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun remoteToEntity(weatherData: CurrentWeatherData): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            weatherData.dt,
            weatherData.main.temp,
            weatherData.weather[0].id,
            weatherData.wind.speed,
            weatherData.main.feelsLike,
            weatherData.main.humidity
        )
    }

    private fun entityToModel(
        weatherEntity: CurrentWeatherEntity?,
        state: State,
        city: String
    ): CurrentWeatherResponse {
        if (weatherEntity == null) {
            return CurrentWeatherResponse(null, state)
        } else {
            val weatherType: WeatherType = when (weatherEntity.weather) {
                in THUNDER_MIN..THUNDER_MAX -> WeatherType.THUNDERSTORM
                in DRIZZLE_MIN..DRIZZLE_MAX -> WeatherType.DRIZZLE
                in RAIN_MIN..RAIN_MAX -> WeatherType.RAIN
                in SNOW_MIN..SNOW_MAX -> WeatherType.SNOW
                in FOG_MIN..FOG_MAX -> WeatherType.FOG
                SUN -> WeatherType.CLEAR
                in CLOUDS_MIN..CLOUDS_MAX -> WeatherType.CLOUDS
                else -> WeatherType.UNKNOWN
            }

            val millis: Long = weatherEntity.time.toLong() * K
            val date = Date(millis)
            val sdf = SimpleDateFormat("dd/MM/yy HH:mm", Locale.FRANCE)
            val formattedDate = sdf.format(date)
            return CurrentWeatherResponse(
                WeatherModel(
                    formattedDate,
                    city,
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