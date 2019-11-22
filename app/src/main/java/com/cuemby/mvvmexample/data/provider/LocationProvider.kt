package com.cuemby.mvvmexample.data.provider

import com.cuemby.mvvmexample.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean

    suspend fun getPreferredLocationString(): String
}