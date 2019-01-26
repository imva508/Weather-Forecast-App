package com.example.weatherforecast.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecast.data.db.entity.CURRENT_WEATHER_ID
import com.example.weatherforecast.data.db.entity.CurrentWeatherEntry
import com.example.weatherforecast.data.db.unitlocalized.ImperialCurrentWeatherEntry
import com.example.weatherforecast.data.db.unitlocalized.MetricCurrentWeatherEntry
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherResponse: CurrentWeatherEntry)

    @Query("SELECT * FROM current_weather WHERE id=$CURRENT_WEATHER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id=$CURRENT_WEATHER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}