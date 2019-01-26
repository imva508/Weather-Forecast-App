package com.example.weatherforecast.ui.weather.current


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.weatherforecast.R
import com.example.weatherforecast.data.network.ApixuWeatherApiService
import com.example.weatherforecast.data.network.ConnectivityInterceptorImpl
import com.example.weatherforecast.data.network.WeatherNetworkDataSource
import com.example.weatherforecast.data.network.WeatherNetworkDataSourceImpl
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrentWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)

        val service = ApixuWeatherApiService(ConnectivityInterceptorImpl(context!!))
        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(service)

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this, Observer {
            textR.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
            weatherNetworkDataSource.fetchCurrentWeather("london", "en")
        }
    }
}
