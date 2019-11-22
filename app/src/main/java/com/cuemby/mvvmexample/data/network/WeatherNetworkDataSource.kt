package com.cuemby.mvvmexample.data.network

import androidx.lifecycle.LiveData
import com.cuemby.mvvmexample.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downladedCurrentWeather: LiveData<CurrentWeatherResponse>
    suspend fun fetchCurrentWeather(
        location: String
    )
}