package com.example.weatherforecast.data

import com.example.weatherforecast.data.response.CurrentWeatherResponse
import kotlinx.coroutines.Deferred
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "18fe8a99644c453fb47133635192501"

// http://api.apixu.com/v1/current.json?key=18fe8a99644c453fb47133635192501&q=Paris

interface ApixuWeatherApiService {

    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") language: String = "en"
    ): Deferred<CurrentWeatherResponse>


    companion object {

        operator fun invoke():ApixuWeatherApiService{

        }
    }
}