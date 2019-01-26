package com.example.weatherforecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.weatherforecast.Internal.UnitSystem
import com.example.weatherforecast.Internal.lazyDeferred
import com.example.weatherforecast.data.db.repository.ForecastRepository
import com.example.weatherforecast.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {

    private val unitSystem: UnitSystem = UnitSystem.METRIC

    private val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }


}