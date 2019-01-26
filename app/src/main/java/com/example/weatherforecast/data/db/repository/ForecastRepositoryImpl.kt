package com.example.weatherforecast.data.db.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.CurrentWeatherDao
import com.example.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.example.weatherforecast.data.network.WeatherNetworkDataSource
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*


class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {
            persistFetchedCurrentWeather(it)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext if (metric) currentWeatherDao.getWeatherMetric() else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(response: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            initWeather()
            currentWeatherDao.upsert(response.currentWeatherEntry)
        }
    }

    private suspend fun initWeather() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() =
        weatherNetworkDataSource.fetchCurrentWeather("London", Locale.getDefault().language)

    private fun isFetchCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}