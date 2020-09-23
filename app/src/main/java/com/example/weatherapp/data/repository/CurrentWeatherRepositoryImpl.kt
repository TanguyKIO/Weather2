package com.example.weatherapp.data.repository


import com.example.weatherapp.data.API_KEY
import com.example.weatherapp.data.db.WeatherDao
import com.example.weatherapp.data.db.CurrentWeatherEntity
import com.example.weatherapp.data.web.CurrentWeatherData
import com.example.weatherapp.data.web.WeatherService
import com.example.weatherapp.domain.entities.*
import com.example.weatherapp.domain.interactor.CurrentWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : CurrentWeatherRepository {

    override fun getCurrentWeather(city: String, units: String): Flow<CurrentWeatherResponse> {
        return flow {
            val cached = weatherDao.getLastCurrent()
            emit(entityToModel(cached, State.LOADING, city))
            delay(1000)
            val weatherEntity =
                remoteToEntity(weatherService.getCurrentWeather(city, units, API_KEY))
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

    override fun getRecommendations(): List<Wears> {
        val recommendation = mutableListOf<Wears>()

        return recommendation
    }

    private fun remoteToEntity(weatherData: CurrentWeatherData): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            weatherData.dt,
            weatherData.main.temp,
            weatherData.weather[0].id,
            weatherData.wind.speed,
            weatherData.main.feels_like,
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