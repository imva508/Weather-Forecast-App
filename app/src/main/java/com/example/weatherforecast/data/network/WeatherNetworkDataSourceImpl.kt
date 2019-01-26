package com.example.weatherforecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.Internal.NoConnectivityException
import com.example.weatherforecast.data.network.response.CurrentWeatherResponse
import java.lang.Exception

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) :
    WeatherNetworkDataSource {

    private val mutableDownloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = mutableDownloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try {
            val fetchedCurrentWeather = apixuWeatherApiService
                .getCurrentWeatherAsync(location, languageCode)
                .await()
            mutableDownloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("CONNECTIVITY", "No internet connection", e)
        }
    }
}