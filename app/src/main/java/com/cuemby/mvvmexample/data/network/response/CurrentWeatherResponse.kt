package com.cuemby.mvvmexample.data.network.response

import com.cuemby.mvvmexample.data.db.entity.CurrentWeatherEntry
import com.cuemby.mvvmexample.data.db.entity.WeatherLocation
import com.cuemby.mvvmexample.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: WeatherLocation,
    val request: Request
)