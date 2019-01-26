package com.example.weatherforecast.data.db.repository

import androidx.lifecycle.LiveData
import com.example.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {

    suspend fun getCurrentWeather(metric:Boolean):LiveData<out UnitSpecificCurrentWeatherEntry>
}