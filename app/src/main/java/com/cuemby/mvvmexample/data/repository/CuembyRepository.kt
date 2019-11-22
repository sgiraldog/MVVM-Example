package com.cuemby.mvvmexample.data.repository

import androidx.lifecycle.LiveData
import com.cuemby.mvvmexample.data.db.entity.CurrentWeatherEntry
import com.cuemby.mvvmexample.data.db.entity.WeatherLocation

interface CuembyRepository {
    suspend fun getCurrentWeather(isMetric: Boolean): LiveData<CurrentWeatherEntry>

    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}