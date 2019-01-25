package com.example.weatherforecast.data

import com.example.weatherforecast.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.intellij.lang.annotations.Language
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

        operator fun invoke(): ApixuWeatherApiService {
            val requestInterceptor = Interceptor {
                val url = it.request().url().newBuilder().addQueryParameter("key", API_KEY).build()
                val request = it.request().newBuilder().url(url).build()
                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()
            return Retrofit.Builder().client(okHttpClient).baseUrl("http://api.apixu.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApixuWeatherApiService::class.java)
        }
    }
}